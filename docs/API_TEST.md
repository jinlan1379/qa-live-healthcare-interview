# API 测试文档

## 前置条件

1. 启动 MySQL：
```bash
cd server/qa-service-user
docker compose up -d
```

2. 启动后端服务：
```bash
cd server/qa-service-user
export JAVA_HOME=~/jdk17/Contents/Home  # 如需指定 JDK
./mvnw spring-boot:run
```

服务运行在 http://localhost:8080

---

## 1. 医生 API

### 1.1 获取所有医生列表

```bash
curl -s http://localhost:8080/api/doctors | python3 -m json.tool
```

**预期结果:** 返回 5 个医生的 JSON 数组，每个医生包含 id、username、name、title、department、avatar、experience、specialties、isActive 字段。

### 1.2 获取在线医生列表

```bash
curl -s http://localhost:8080/api/doctors/active | python3 -m json.tool
```

**预期结果:** 返回 isActive=true 的医生列表（4 个）。

### 1.3 根据用户名获取医生

```bash
curl -s http://localhost:8080/api/doctors/dr-zhang-wei | python3 -m json.tool
```

**预期结果:** 返回张伟医生的信息。

### 1.4 获取不存在的医生

```bash
curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/api/doctors/not-exist
```

**预期结果:** HTTP 状态码 404。

### 1.5 医生登录 - 成功

```bash
curl -s -X POST http://localhost:8080/api/doctors/login \
  -H "Content-Type: application/json" \
  -d '{"username":"dr-zhang-wei","password":"123456"}' | python3 -m json.tool
```

**预期结果:**
```json
{
  "success": true,
  "message": "登录成功",
  "user": { "username": "dr-zhang-wei", "name": "张伟医生", ... }
}
```

### 1.6 医生登录 - 失败

```bash
curl -s -X POST http://localhost:8080/api/doctors/login \
  -H "Content-Type: application/json" \
  -d '{"username":"dr-zhang-wei","password":"wrong"}' | python3 -m json.tool
```

**预期结果:**
```json
{
  "success": false,
  "message": "用户名或密码错误",
  "user": null
}
```

---

## 2. 患者 API

### 2.1 患者登录 - 成功

```bash
curl -s -X POST http://localhost:8080/api/patients/login \
  -H "Content-Type: application/json" \
  -d '{"username":"patient001","password":"123456"}' | python3 -m json.tool
```

**预期结果:**
```json
{
  "success": true,
  "message": "登录成功",
  "user": { "id": 1, "username": "patient001", "name": "赵明", ... }
}
```

### 2.2 患者登录 - 失败

```bash
curl -s -X POST http://localhost:8080/api/patients/login \
  -H "Content-Type: application/json" \
  -d '{"username":"patient001","password":"wrong"}' | python3 -m json.tool
```

**预期结果:**
```json
{
  "success": false,
  "message": "用户名或密码错误",
  "user": null
}
```

### 2.3 患者注册 - 成功

```bash
curl -s -X POST http://localhost:8080/api/patients/register \
  -H "Content-Type: application/json" \
  -d '{"username":"newpatient","password":"123456","name":"新患者"}' | python3 -m json.tool
```

**预期结果:**
```json
{
  "success": true,
  "message": "注册成功",
  "user": { "id": 6, "username": "newpatient", "name": "新患者", ... }
}
```

### 2.4 患者注册 - 用户名已存在

```bash
curl -s -X POST http://localhost:8080/api/patients/register \
  -H "Content-Type: application/json" \
  -d '{"username":"patient001","password":"123456","name":"测试"}' | python3 -m json.tool
```

**预期结果:**
```json
{
  "success": false,
  "message": "用户名已存在",
  "user": null
}
```

---

## 3. 健康检查

```bash
curl -s http://localhost:8080/actuator/health | python3 -m json.tool
```

**预期结果:** 返回 `{"status": "UP"}` 及数据库连接状态。

---

## 4. CORS 测试

```bash
curl -s http://localhost:8080/api/test/cors | python3 -m json.tool
```

**预期结果:** 返回 CORS 测试成功信息。

---

## 5. phpMyAdmin

浏览器访问 http://localhost:8081

- 服务器: mysql
- 用户名: root
- 密码: root123

可查看 `qa_healthcare` 数据库中的 `doctor` 和 `patient` 表数据。
