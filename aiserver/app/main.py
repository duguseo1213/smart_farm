import os
import base64
from PlantDisease import MyCnnModel
from PlantDisease import PlantDiseaseLabel
from AnimalDetection import animalDetectionLabel
from dotenv import load_dotenv
from fastapi import FastAPI, File, UploadFile, Form, Query
from fastapi.middleware.cors import CORSMiddleware
import torch
from torchvision import transforms
from PIL import Image
from ultralytics import YOLO
import io
from openai import OpenAI
import shutil


def encode_image(image_path):
  with open(image_path, "rb") as image_file:
    return base64.b64encode(image_file.read()).decode('utf-8')

load_dotenv() 
app = FastAPI()
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

script_dir = os.path.dirname(os.path.abspath(__file__))

# 전체 상수
img_height = 224
img_width = 224

# 상수
Plant_Disease_Detection_Labels = 45
Plant_Disease_Detection_State_Dict_PATH = os.path.join(script_dir,"PlantDisease/plant.pth")
Animal_Detection_Model_PATH = os.path.join(script_dir,"AnimalDetection/best.pt")
Human_Detection_Model_PATH = os.path.join(script_dir,"AnimalDetection/best_human.pt")
# plantDiseaseDetection
plant_disease_detection_model = MyCnnModel.MyCnn(Plant_Disease_Detection_Labels)
plant_disease_detection_model.load_state_dict(torch.load(Plant_Disease_Detection_State_Dict_PATH, map_location=torch.device('cpu')))
plant_disease_detection_model.eval()
# 이미지 전처리 plantDiseaseDetection
plant_disease_image_transform = transforms.Compose([
    transforms.Resize((img_height, img_width)),
    transforms.ToTensor()
])

# animal Detection Model
animal_detection_model = YOLO(Animal_Detection_Model_PATH)
human_detection_model = YOLO(Human_Detection_Model_PATH)

# OpenAI API 키 설정
# openai.api_key =  os.getenv('OPEN_AI_APT_KEY')

@app.post("/plantDiseaseDetection")
async def plantDiseaseDetection(imageFile: UploadFile = File(...)):

    image_data = await imageFile.read()  # UploadFile에서 데이터 읽기
    image = Image.open(io.BytesIO(image_data))  # bytes 데이터를 PIL 이미지로 변환

    transformed_image = plant_disease_image_transform(image)
    transformed_image = transformed_image.unsqueeze(0) 
    # 모델로 값 추측
    answer = plant_disease_detection_model(transformed_image)
    probabilities = torch.softmax(answer, dim=1)

    # 가장 높은 확률의 인덱스 찾기
    predicted_index = torch.argmax(probabilities, dim=1).item()
    # 나온 값 정제 후 리턴
    return {"diseaseName":PlantDiseaseLabel.labels[predicted_index], "diseaseSolvent":PlantDiseaseLabel.solutions[predicted_index]}

@app.post("/animalDetection")
async def animalDetection(imageFile: UploadFile = File(...)):

    image_data = await imageFile.read()  # UploadFile에서 데이터 읽기
    image = Image.open(io.BytesIO(image_data))  # bytes 데이터를 PIL 이미지로 변환

    result = animal_detection_model(image)
    detections = result[0]  # 첫 번째 이미지의 결과 가져오기

    # 가장 신뢰도가 높은 탐지 결과 초기화
    detections = result[0]  # 첫 번째 이미지의 결과 가져오기

    # 가장 신뢰도가 높은 탐지 결과 초기화
    highest_confidence = 0
    best_class_name = None

    # 탐지된 객체를 반복하며 신뢰도가 가장 높은 객체 찾기
    for detection in detections.boxes:
        confidence = detection.conf[0].item()  # 신뢰도
        if confidence > highest_confidence and confidence >= 0.5:  # 신뢰도가 50% 이상인지 확인
            highest_confidence = confidence
            class_id = int(detection.cls[0])  # 클래스 ID
            best_class_name = animalDetectionLabel.class_names.get(class_id, "Unknown")  # 클래스 이름 가져오기

    print(best_class_name)
    # 클래스 이름 출력
    if best_class_name is not None:
        # print({"isHarm": True, "HarmAnimalType": best_class_name, "HarmPictureId":1})
        return {best_class_name}
    # print({"isHarm": False, "HarmAnimalType": "none", "HarmPictureId":1})
    result2 = human_detection_model(image)
    detections2 = result2[0]
    print("second")
    highest_confidence = 0
    best_class_name = None
    
    for detection in detections2.boxes:
        confidence = detection.conf[0].item()  # 신뢰도
        if confidence >= 0.5:  # 신뢰도가 50% 이상인지 확인
            return {"Human"}
            
    return {"none"}




@app.post("/LettuceSegment")
async def LettuceSegment(imageFile: UploadFile = File(...)):
    
    client = OpenAI(api_key=os.getenv("OPEN_AI_APT_KEY"))
    
    with open("temp_image.png", "wb") as buffer:
        shutil.copyfileobj(imageFile.file, buffer)

    image_path = "temp_image.png"

    # Getting the base64 string
    base64_image = encode_image(image_path)

    response = client.chat.completions.create(
    model="gpt-4o-mini",
    messages=[
        {
        "role": "user",
        "content": [
            {
            "type": "text",
            "text": "Please tell me the growth stage of the crop as a number between 1 and 5. If it is not a crop, respond with 0.",
            },
            {
            "type": "image_url",
            "image_url": {
                "url":  f"data:image/jpeg;base64,{base64_image}"
            },
            },
        ],
        }
    ],
    )
    print(response.choices[0])
    return {"response": response.choices[0].message.content}



    