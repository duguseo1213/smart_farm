INSERT INTO WCDFarm.devices (registered_date, device_id, stream_key, device_status) VALUES
('2023-11-01', 1,'cctv001.stream', 'AVAILABLE'),
('2023-10-29', 2,'cctv002.stream', 'AVAILABLE'),
('2023-10-28', 3,'cctv003.stream', 'AVAILABLE');

INSERT INTO WCDFarm.gardens (created_date, device_id, garden_id, garden_address, garden_image, garden_name, lat, lon) VALUES
('2023-11-01', 1, 1, '서울시 강남구', 'https://c207-bucket.s3.ap-southeast-2.amazonaws.com/35543_13467_3220.jpg', '강남 정원',37.514575,127.0495556),
('2023-11-02', 2, 2, '부산시 해운대구','https://c207-bucket.s3.ap-southeast-2.amazonaws.com/unnamed.jpg', '해운대 정원',35.16001944,129.1658083),
('2023-11-03', 3, 3, '대구시 수성구', 'https://c207-bucket.s3.ap-southeast-2.amazonaws.com/UP3509032A61974.jpg', '수성 정원',35.85520833,128.6328667);

INSERT INTO WCDFarm.gallery_picture (crated_date, gallery_picture_id, garden_id, gallery_image, gallery_image_description) VALUES
('2023-11-02', 2, 1, 'https://c207-bucket.s3.ap-southeast-2.amazonaws.com/unnamed.jpg', '두 번째 정원의 사진'),
('2023-11-03', 3, 2, 'https://c207-bucket.s3.ap-southeast-2.amazonaws.com/UP3509032A61974.jpg', '세 번째 정원의 사진'),
('2023-11-04', 4, 3, 'https://c207-bucket.s3.ap-southeast-2.amazonaws.com/35543_13467_3220.jpg', '네 번째 정원의 사진'),
('2023-11-13', 13, 1, 'https://c207-bucket.s3.ap-southeast-2.amazonaws.com/35543_13467_3220.jpg', '열세 번째 정원의 사진'),
('2023-11-14', 14, 2, 'https://c207-bucket.s3.ap-southeast-2.amazonaws.com/unnamed.jpg', '열네 번째 정원의 사진'),
('2023-11-15', 15, 3, 'https://c207-bucket.s3.ap-southeast-2.amazonaws.com/UP3509032A61974.jpg', '열다섯 번째 정원의 사진'),
('2023-11-24', 24, 1, 'https://c207-bucket.s3.ap-southeast-2.amazonaws.com/UP3509032A61974.jpg', '스물네 번째 정원의 사진'),
('2023-11-25', 25, 2, 'https://c207-bucket.s3.ap-southeast-2.amazonaws.com/35543_13467_3220.jpg', '스물다섯 번째 정원의 사진'),
('2023-11-26', 26, 3, 'https://c207-bucket.s3.ap-southeast-2.amazonaws.com/unnamed.jpg', '스물여섯 번째 정원의 사진');
