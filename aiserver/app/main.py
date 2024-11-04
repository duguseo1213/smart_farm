import os
from PlantDisease import MyCnnModel
from PlantDisease import PlantDiseaseLabel
from dotenv import load_dotenv
from fastapi import FastAPI, File, UploadFile, Form, Query
from fastapi.middleware.cors import CORSMiddleware
import torch
from torchvision import transforms
from PIL import Image
import io

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

# plantDiseaseDetection의 상수
Plant_Disease_Detection_Labels = 45
Plant_Disease_Detection_State_Dict_PATH = os.path.join(script_dir,"PlantDisease/plant.pth")

# plantDiseaseDetection
plant_disease_detection_model = MyCnnModel.MyCnn(Plant_Disease_Detection_Labels)
plant_disease_detection_model.load_state_dict(torch.load(Plant_Disease_Detection_State_Dict_PATH, weights_only=True))
plant_disease_detection_model.eval()

# 이미지 전처리 plantDiseaseDetection

plant_disease_image_transform = transforms.Compose([
    transforms.Resize((img_height, img_width)),
    transforms.ToTensor()
])

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


    