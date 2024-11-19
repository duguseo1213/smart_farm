import os

# 텍스트 파일이 있는 폴더 경로
folder_path = "C:\\Users\\SSAFY\\Documents\\dataset\\HUMANDETECTION.v1i.yolov8\\valid\\labels"

# 폴더 내의 모든 텍스트 파일을 가져오기
for filename in os.listdir(folder_path):
    print("실행")
    if filename.endswith(".txt"):
        file_path = os.path.join(folder_path, filename)
        
        # 파일 내용 읽기
        with open(file_path, 'r') as file:
            lines = file.readlines()
        
        # 수정된 내용을 저장할 리스트
        modified_lines = []
        
        for line in lines:
            # 각 줄의 앞부분을 "HUMAN"으로 변경
            modified_line = line.replace(line.split()[0], "18", 1)
            modified_lines.append(modified_line)
        
        # 수정된 내용을 파일에 다시 쓰기
        with open(file_path, 'w') as file:
            file.writelines(modified_lines)

print("모든 파일에서 숫자가 'HUMAN'으로 변경되었습니다.")