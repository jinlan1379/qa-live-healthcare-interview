CREATE DATABASE IF NOT EXISTS qa_healthcare DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE qa_healthcare;

CREATE TABLE IF NOT EXISTS doctor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    title VARCHAR(50),
    department VARCHAR(50),
    avatar VARCHAR(500),
    experience VARCHAR(100),
    specialties VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    birthday DATE,
    phone VARCHAR(20),
    gender VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO doctor (username, password, name, title, department, avatar, experience, specialties, is_active) VALUES
('dr-zhang-wei', '123456', '张伟医生', '主任医师', '心内科', 'https://images.pexels.com/photos/5215024/pexels-photo-5215024.jpeg?auto=compress&cs=tinysrgb&w=400', '15年临床经验', '高血压,冠心病,心律失常', true),
('dr-li-na', '123456', '李娜医生', '副主任医师', '儿科', 'https://images.pexels.com/photos/5327585/pexels-photo-5327585.jpeg?auto=compress&cs=tinysrgb&w=400', '10年临床经验', '儿童感冒,儿童发育,疫苗接种', true),
('dr-wang-qiang', '123456', '王强医生', '主治医师', '骨科', 'https://images.pexels.com/photos/5452293/pexels-photo-5452293.jpeg?auto=compress&cs=tinysrgb&w=400', '8年临床经验', '骨折,关节炎,运动损伤', true),
('dr-liu-min', '123456', '刘敏医生', '主任医师', '妇产科', 'https://images.pexels.com/photos/5452201/pexels-photo-5452201.jpeg?auto=compress&cs=tinysrgb&w=400', '18年临床经验', '孕期保健,妇科炎症,产后恢复', false),
('dr-chen-jie', '123456', '陈杰医生', '副主任医师', '消化内科', 'https://images.pexels.com/photos/5215024/pexels-photo-5215024.jpeg?auto=compress&cs=tinysrgb&w=400', '12年临床经验', '胃炎,肠道疾病,肝病', true);

INSERT INTO patient (username, password, name, birthday, phone, gender) VALUES
('patient001', '123456', '赵明', '1985-03-15', '138****1234', '男'),
('patient002', '123456', '钱丽', '1990-07-22', '139****5678', '女'),
('patient003', '123456', '孙磊', '1978-12-03', '137****9012', '男'),
('patient004', '123456', '李芳', '1995-05-18', '136****3456', '女'),
('patient005', '123456', '周杰', '1988-09-30', '135****7890', '男');
