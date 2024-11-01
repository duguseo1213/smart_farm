import os
from PlantDisease import MyCnnModel
from dotenv import load_dotenv
from fastapi import FastAPI, File, UploadFile, Form, Query
from fastapi.middleware.cors import CORSMiddleware

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

@app.post("/plantDiseaseDetection")
async def plantDiseaseDetection(imageFile: UploadFile = File(...)):
    # 모델 로드
    model = MyCnnModel.MyCnn(Plant_Disease_Detection_Labels)
    model.load_state_dict(torch.load(Plant_Disease_Detection_State_Dict_PATH))
    
    # 들어온 이미지 정해진 크기로 변환
    image_data = await imageFile.read()
    
    # 모델로 값 추측
    model.eval()
    
    # 나온 값 정제 후 리턴
    return {"diseaseName":"test1", "diseaseSolvent":"test2"}
    