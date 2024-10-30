import os
import shutil
import random

def split_dataset(source_dir, train_dir, valid_dir, split_ratio=0.8):
    # 각 클래스 폴더에 대해 반복
    for class_name in os.listdir(source_dir):
        class_path = os.path.join(source_dir, class_name)
        
        # 폴더인지 확인
        if os.path.isdir(class_path):
            # 이미지 파일 목록 가져오기
            images = [f for f in os.listdir(class_path) if f.endswith(('jpg', 'jpeg', 'png'))]
            random.shuffle(images)  # 이미지를 무작위로 섞기
            
            # 훈련 데이터와 검증 데이터 나누기
            split_index = int(len(images) * split_ratio)
            train_images = images[:split_index]
            valid_images = images[split_index:]
            new_class_name="Lattuce___" + class_name
            # train과 valid 폴더 생성
            os.makedirs(os.path.join(train_dir,new_class_name), exist_ok=True)
            os.makedirs(os.path.join(valid_dir,new_class_name), exist_ok=True)

            # 파일을 train 폴더로 이동
            for image in train_images:
                shutil.copy(os.path.join(class_path, image), os.path.join(train_dir, new_class_name, image))

            # 파일을 valid 폴더로 이동
            for image in valid_images:
                shutil.copy(os.path.join(class_path, image), os.path.join(valid_dir, new_class_name, image))

# 사용 예시
source_directory = 'C:\\Users\\SSAFY\\Documents\\dataset\\새 폴더\\Lettuce_disease_datasets'  # 원본 이미지가 있는 폴더
train_directory = 'C:\\Users\\SSAFY\\Documents\\dataset\\새 폴더\\train'     # 훈련 데이터 폴더
valid_directory = 'C:\\Users\\SSAFY\\Documents\\dataset\\새 폴더\\valid'     # 검증 데이터 폴더

split_dataset(source_directory, train_directory, valid_directory)