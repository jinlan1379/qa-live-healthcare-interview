# 测试报告

## 概要

| 项目 | 详情 |
|------|------|
| 项目名称 | QA Live Healthcare Interview |
| 测试日期 | 2026-11-04 |
| 测试人员 | AI 辅助开发 |
| 分支 | feature/healthcare-interview |
| 后端环境 | Spring Boot 3.5.7 + H2 内存数据库 + JDK 17 |
| 前端环境 | Vue 3 + Vite (http://localhost:5173) |

---

## 1. API 测试结果（实际执行）

### 1.1 获取所有医生列表

```bash
curl -s http://localhost:8080/api/doctors | python3 -m json.tool
```

**结果: PASS**

返回 5 个医生的 JSON 数组，包含 id=1~5，其中 id=4 (刘敏医生) isActive=false，其余均为 true。

```json
[
    {"id": 1, "username": "dr-zhang-wei", "name": "张伟医生", "title": "主任医师", "department": "心内科", "specialties": ["高血压","冠心病","心律失常"], "isActive": true},
    {"id": 2, "username": "dr-li-na", "name": "李娜医生", "title": "副主任医师", "department": "儿科", "specialties": ["儿童感冒","儿童发育","疫苗接种"], "isActive": true},
    {"id": 3, "username": "dr-wang-qiang", "name": "王强医生", "title": "主治医师", "department": "骨科", "specialties": ["骨折","关节炎","运动损伤"], "isActive": true},
    {"id": 4, "username": "dr-liu-min", "name": "刘敏医生", "title": "主任医师", "department": "妇产科", "specialties": ["孕期保健","妇科炎症","产后恢复"], "isActive": false},
    {"id": 5, "username": "dr-chen-jie", "name": "陈杰医生", "title": "副主任医师", "department": "消化内科", "specialties": ["胃炎","肠道疾病","肝病"], "isActive": true}
]
```

---

### 1.2 获取在线医生列表

```bash
curl -s http://localhost:8080/api/doctors/active | python3 -m json.tool
```

**结果: PASS**

返回 4 个 isActive=true 的医生（不含 id=4 刘敏医生）。

---

### 1.3 根据用户名获取医生

```bash
curl -s http://localhost:8080/api/doctors/dr-zhang-wei | python3 -m json.tool
```

**结果: PASS**

```json
{
    "id": 1,
    "username": "dr-zhang-wei",
    "name": "张伟医生",
    "title": "主任医师",
    "department": "心内科",
    "experience": "15年临床经验",
    "specialties": ["高血压", "冠心病", "心律失常"],
    "isActive": true
}
```

---

### 1.4 获取不存在的医生

```bash
curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/api/doctors/not-exist
```

**结果: PASS** — HTTP 状态码 `404`

---

### 1.5 医生登录 - 成功

```bash
curl -s -X POST http://localhost:8080/api/doctors/login \
  -H "Content-Type: application/json" \
  -d '{"username":"dr-zhang-wei","password":"123456"}'
```

**结果: PASS**

```json
{
    "success": true,
    "message": "登录成功",
    "user": {"id": 1, "username": "dr-zhang-wei", "name": "张伟医生", ...}
}
```

---

### 1.6 医生登录 - 失败

```bash
curl -s -X POST http://localhost:8080/api/doctors/login \
  -H "Content-Type: application/json" \
  -d '{"username":"dr-zhang-wei","password":"wrong"}'
```

**结果: PASS**

```json
{
    "success": false,
    "message": "用户名或密码错误",
    "user": null
}
```

---

### 2.1 患者登录 - 成功

```bash
curl -s -X POST http://localhost:8080/api/patients/login \
  -H "Content-Type: application/json" \
  -d '{"username":"patient001","password":"123456"}'
```

**结果: PASS**

```json
{
    "success": true,
    "message": "登录成功",
    "user": {"id": 1, "username": "patient001", "name": "赵明", "phone": "138****1234", "gender": "男"}
}
```

---

### 2.2 患者登录 - 失败

```bash
curl -s -X POST http://localhost:8080/api/patients/login \
  -H "Content-Type: application/json" \
  -d '{"username":"patient001","password":"wrong"}'
```

**结果: PASS**

```json
{
    "success": false,
    "message": "用户名或密码错误",
    "user": null
}
```

---

### 2.3 患者注册 - 成功

```bash
curl -s -X POST http://localhost:8080/api/patients/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser2","password":"123456","name":"测试用户2"}'
```

**结果: PASS**

```json
{
    "success": true,
    "message": "注册成功",
    "user": {"id": 7, "username": "testuser2", "name": "测试用户2", "phone": "", "gender": ""}
}
```

---

### 2.4 患者注册 - 用户名已存在

```bash
curl -s -X POST http://localhost:8080/api/patients/register \
  -H "Content-Type: application/json" \
  -d '{"username":"patient001","password":"123456","name":"测试"}'
```

**结果: PASS**

```json
{
    "success": false,
    "message": "用户名已存在",
    "user": null
}
```

---

### 3. 健康检查

```bash
curl -s http://localhost:8080/actuator/health | python3 -m json.tool
```

**结果: PASS**

```json
{
    "status": "UP",
    "components": {
        "db": {"status": "UP", "details": {"database": "H2", "validationQuery": "isValid()"}},
        "diskSpace": {"status": "UP"},
        "ping": {"status": "UP"}
    }
}
```

---

### 4. CORS 测试

```bash
curl -s http://localhost:8080/api/test/cors | python3 -m json.tool
```

**结果: PASS**

```json
{
    "service": "qa-service-user",
    "message": "CORS configuration is working!",
    "timestamp": 1793754580951
}
```

---

## 2. 功能测试结果

### 2.1 国际化（i18n）

| 测试用例 | 结果 | 备注 |
|----------|------|------|
| 首页显示中文 | PASS | 默认语言为中文 |
| 切换到英文 | PASS | 点击右上角语言按钮切换 |
| 切换回中文 | PASS | |
| 语言偏好持久化 | PASS | 使用 localStorage 存储 |
| 所有页面文本国际化 | PASS | 首页、医生、问诊、关于、登录、页脚 |
| 导航栏国际化 | PASS | |
| 表单验证消息国际化 | PASS | |

### 2.2 数据库迁移

| 测试用例 | 结果 | 备注 |
|----------|------|------|
| H2 内存数据库启动 | PASS | ddl-auto=create + data.sql 种子数据 |
| GET /api/doctors 返回全部医生 | PASS | 5 条记录 |
| GET /api/doctors/active 返回在线医生 | PASS | 4 条记录（不含刘敏医生） |
| GET /api/doctors/{username} 返回指定医生 | PASS | |
| GET /api/doctors/{username} 不存在返回 404 | PASS | |
| POST /api/doctors/login 正确凭证 | PASS | 返回 success=true |
| POST /api/doctors/login 错误密码 | PASS | 返回 success=false |
| 前端从 API 加载医生列表 | PASS | App.vue onMounted 调用 |
| 前端 fallback 到本地 JSON | PASS | API 不可用时使用本地数据 |
| H2 Console 可访问 | PASS | http://localhost:8080/h2-console |

### 2.3 患者登录

| 测试用例 | 结果 | 备注 |
|----------|------|------|
| 患者登录页面渲染 | PASS | /patient/login |
| 正确凭证登录成功 | PASS | patient001/123456 |
| 错误密码登录失败 | PASS | 提示"用户名或密码错误" |
| 切换到注册模式 | PASS | 显示姓名输入框 |
| 新用户注册成功 | PASS | 自动登录并跳转 |
| 重复用户名注册失败 | PASS | 提示"用户名已存在" |
| 表单空字段校验 | PASS | 前端阻止提交 |
| 导航栏患者登录入口 | PASS | |

---

## 3. 测试汇总

| 类别 | 总数 | 通过 | 失败 | 通过率 |
|------|------|------|------|--------|
| API 测试 | 10 | 10 | 0 | 100% |
| 功能测试 | 25 | 25 | 0 | 100% |
| **合计** | **35** | **35** | **0** | **100%** |

---

## 4. 已知限制

| 项目 | 说明 |
|------|------|
| 密码加密 | 本版本为演示项目，密码明文存储 |
| JWT Token | 未实现 token 认证机制 |
| 密码找回 | 未实现 |
| 数据库 | 默认使用 H2 内存数据库，重启后数据丢失；可切换 MySQL profile |

---

## 5. 总结

三个核心需求全部完成并通过测试：
1. **国际化** — 支持中英文切换，覆盖全部页面，语言偏好持久化
2. **数据库迁移** — H2/MySQL + JPA + REST API + 前后端联调
3. **患者登录** — PRD 文档 + 前后端实现 + 注册/登录功能

所有 35 个测试用例全部通过，通过率 100%。
