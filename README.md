<img src="https://capsule-render.vercel.app/api?type=waving&color=BDBDC8&height=150&section=header" width="100%" >

# 프로젝트명 : 팜요 Farm-us
![App1](Picture/farmus.png)
- 배포 URL : https://drive.google.com/file/d/1OseafIsXObwfuNPSEKQ_w3IaEkS9iNXR/view?usp=sharing
- Test ID : evilparan@gmail.com
- Test PW : farmustest1!

## 개요

 : 주말 농장을 이용하는 직장인, 가족들의 편의성을 증대하고, 농장주의 관리 효율을 높이는 스마트 텃밭 관리 프로젝트입니다.

## 기능

- 사용자 친화적인 앱을 통해 다양한 서비스를 제공합니다.
    1. 원격 급수
    2. 실시간 텃밭 CCTV
    3. 동물 침입, 도난 감지
    4. 추억 사진 촬영
    5. 질병 확인
    6. 수확시기 예측
    7. 날씨 및 토양 정보 제공
- 텃밭에 설치된 하드웨어를 통해 각종 정보를 수집합니다.
- 젯슨 나노를 통해 침입 동물, 도난을 온보드로 탐지합니다.

## 개발 환경

- AI : ResNet-152, CoAtNet-6, Coca
- Front : Android Kotlin
- Back : FastAPI, Spring, NGINX, Redis, MongoDB, Rabbit, Docker, Amazon EC2, Amazon S3
- Embedded : Jetson Nano, Raspberry Pi, Camera, IR Sensor
- Design : Figma
- 협업 툴 : Notion, Jira, Mattermost


## 시연 영상

<div align="center">
    [![Name](logo_link)](youtube_link)
</div>

<div align="center">
    [![Name](logo_link)](youtube_link)
</div>

<div align="center">
    [![Name](logo_link)](youtube_link)
</div>

### App
<img src="Picture/app1.png" alt="App1" width="200px">
![App1](Picture/app1.png)
![App2](Picture/app2.png)
![App3](Picture/app3.png)
![App4](Picture/app4.png)
![App5](Picture/app5.png)
![App6](Picture/app6.png)



### Hardware
![Hardware](Picture/hard1.png)
![Hardware](Picture/hard2.png)
![Hardware](Picture/hard3.jpg)
![Hardware](Picture/hard4.jpg)
![Hardware](Picture/hard5.jpg)
![Hardware](Picture/hard6.jpg)
![Hardware](Picture/hard7.jpg)


## Architecture

<div align="center">

  <p align="center">
    <img src="/uploads/6654f644ab3a1ec5c947bc31bb8c2a6d/그림1.png">
  </p>
</div>

## ERD
<div align="center">
  <p align="center">
    <img src="/uploads/6de67c4d6230a3e0033dc43a315ea2a8/WCDFarm.png">
  </p>
</div>

### 개발 기간

- 2024-10-14 ~ 2024-11-22

### 팀 소개

<div align="center">

<table>
  <tbody>
    <tr>
      <td align="center">
        <img src="Picture/jongha.png" alt="EM 팀장 : 이종하" width="100px"/>
        <br />
        <sub><b>EM 팀장 : 이종하</b></sub>
      </td>
      <td align="center">
        <img src="Picture/dongin.png" alt="BE : 서동인" width="100px"/>
        <br />
        <sub><b>BE : 서동인</b></sub>
      </td>
      <td align="center">
        <img src="Picture/hyeseong.png" alt="FE : 오혜성" width="100px"/>
        <br />
        <sub><b>FE : 오혜성</b></sub>
      </td>
    </tr>
    <tr>
      <td>
        <ul>
          <li><strong>SSAFY 11th Embedded</strong></li>
          <li>팀장</li>
          <li>하드웨어 제작</li>
          <li>CCTV 녹화 및 동물 탐지후 서버 전송</li>
        </ul>
      </td>
      <td>
        <ul>
          <li><strong>SSAFY 11th Java</strong></li>
          <li>RestAPI개발 및 mqtt서버 구축</li>
          <li>CI/CD 인프라 구축</li>
          <li>rtmp 미디어 서버 구축</li>
        </ul>
      </td>
      <td>
        <ul>
          <li><strong>SSAFY 11th Embedded</strong></li>
          <li>앱 설계</li>
          <li>앱 개발</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td align="center">
        <img src="Picture/hyeondae.jpg" alt="EM : 여현대" width="100px"/>
        <br />
        <sub><b>EM : 여현대</b></sub>
      </td>
      <td align="center">
        <img src="Picture/beomsu.png" alt="BE : 박범수" width="100px"/>
        <br />
        <sub><b>BE : 박범수</b></sub>
      </td>
      <td align="center">
        <img src="Picture/hyorin.png" alt="FE : 강효린" width="100px"/>
        <br />
        <sub><b>FE : 강효린</b></sub>
      </td>
    </tr>
    <tr>
      <td>
        <ul>
          <li><strong>SSAFY 11th Embedded</strong></li>
          <li>하드웨어 제작</li>
          <li>온습도 센서, 토양습도센서, 조도센서 값 측정</li>
          <li>rtmp 서버 전송</li>
        </ul>
      </td>
      <td>
        <ul>
          <li><strong>SSAFY 11th Java</strong></li>
          <li>RestAPI개발</li>
          <li>AI 학습 및 AI Server 구축</li>
          <li>Oauth를 활용한 회원 시스템 개발</li>
        </ul>
      </td>
      <td>
        <ul>
          <li><strong>SSAFY 11th Embedded</strong></li>
          <li>UI 구성 및 구현</li>
          <li>발표자료 제작</li>
        </ul>
      </td>
    </tr>
  </tbody>
</table>

</div>


### 개선 목표
<div>
  <ul>
      <li>텃밭 커뮤니티 기능을 통한 인싸 되기</li>
      <li>On Device AI 기능을 통한 AI서버 과부하 줄이기</li>
      <li>집수 시스템을 통한 물절약</li>
  </ul>
</div>
