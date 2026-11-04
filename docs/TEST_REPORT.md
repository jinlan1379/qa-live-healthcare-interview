# 测试报告

## 概要

| 项目 | 详情 |
|------|------|
| 项目名称 | QA Live Healthcare Interview |
| 测试日期 | 2026-11-04 |
| 测试人员 | AI 辅助开发 |
| 分支 | feature/healthcare-interview |

---

## 1. 功能测试结果

### 1.1 国际化（i18n）

| 测试用例 | 结果 | 备注 |
|----------|------|------|
| 首页显示中文 | PASS | 默认语言为中文 |
| 切换到英文 | PASS | 点击右上角语言按钮切换 |
| 切换回中文 | PASS | |
| 语言偏好持久化 | PASS | 使用 localStorage 存储 |
| 所有页面文本国际化 | PASS | 首页、医生、问诊、关于、登录、页脚 |
| 导航栏国际化 | PASS | |
| 表单验证消息国际化 | PASS | |

### 1.2 数据库迁移

| 测试用例 | 结果 | 备注 |
|----------|------|------|
| Docker Compose 启动 MySQL | PASS | MySQL 8.0 + phpMyAdmin |
| 数据库初始化（init.sql） | PASS | 建表 + 种子数据 |
| GET /api/doctors 返回全部医生 | PASS | 5 条记录 |
| GET /api/doctors/active 返回在线医生 | PASS | 4 条记录 |
| GET /api/doctors/{username} 返回指定医生 | PASS | |
| GET /api/doctors/{username} 不存在返回 404 | PASS | |
| POST /api/doctors/login 正确凭证 | PASS | |
| POST /api/doctors/login 错误密码 | PASS | 返回 success=false |
| 前端从 API 加载医生列表 | PASS | App.vue onMounted 调用 |
| 前端 fallback 到本地 JSON | PASS | API 不可用时使用本地数据 |
| phpMyAdmin 可访问 | PASS | http://localhost:8081 |

### 1.3 患者登录

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
| PRD 模板创建 | PASS | docs/PRD_TEMPLATE.md |
| 患者登录 PRD 创建 | PASS | docs/PRD_PatientLogin.md |

---

## 2. 代码编译测试

| 测试项 | 结果 | 备注 |
|--------|------|------|
| 前端 TypeScript 编译 | PASS | vue-tsc --noEmit |
| 后端 Maven 编译 | PASS | JDK 17, Spring Boot 3.5.7 |

---

## 3. 未实现/已知限制

| 项目 | 说明 |
|------|------|
| 密码加密 | 本版本为演示项目，密码明文存储 |
| JWT Token | 未实现 token 认证机制 |
| 密码找回 | 未实现 |
| 前端构建 | vite build 未做生产环境验证 |
| 单元测试 | 后端未编写单元测试 |

---

## 4. 总结

三个核心需求全部完成：
1. **国际化** - 支持中英文切换，覆盖全部页面
2. **数据库迁移** - MySQL + Docker Compose + JPA + REST API + 前后端联调
3. **患者登录** - PRD + 前后端实现 + 注册/登录功能

所有文档已创建：
- `docs/PRD_TEMPLATE.md` - PRD 模板
- `docs/PRD_PatientLogin.md` - 患者登录 PRD
- `docs/prompts.md` - AI 提示词记录
- `docs/API_TEST.md` - API 测试文档
- `docs/TEST_REPORT.md` - 测试报告（本文件）
