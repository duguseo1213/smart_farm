# Base image
FROM python:3.10

# Set the working directory
WORKDIR /app

# 필수 라이브러리 설치
RUN apt-get update && apt-get install -y libgl1-mesa-glx

# Copy the requirements file into the container
COPY requirements.txt .

# Install dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the entire application into the container
COPY ./app /app

# Expose the port that your FastAPI app runs on
EXPOSE 8000

# Command to run the FastAPI app using Uvicorn
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000", "--reload"]