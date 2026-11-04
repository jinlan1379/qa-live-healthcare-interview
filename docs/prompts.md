# AI Prompts 记录

本文档记录开发过程中使用的所有 AI 提示词。

## 1. 项目探索与规划

**Prompt:**
> 查看 github https://github.com/ups216/qa-live-healthcare-interview 是要干啥？

**结果:** AI 分析了仓库的 README，总结了三个任务：国际化、数据库迁移、用户登录。

---

**Prompt:**
> 开始做吧，先 fork 仓库

**结果:** AI 执行 `gh repo fork` 命令 fork 仓库到个人账号。

---

**Prompt:**
> clone 下来吧，建个 feature 分支

**结果:** AI clone 仓库并创建 `feature/healthcare-interview` 分支。

---

## 2. 任务 1：国际化（i18n）

**Prompt:**
> 是的，自己逐一都做吧，不要找我确认了

**AI 执行的操作:**
1. 安装 `vue-i18n@9`
2. 创建 `src/locales/` 目录
3. 创建 `zh-CN.json` 和 `en-US.json` 翻译文件（覆盖所有页面文本）
4. 创建 `locales/index.ts` 配置文件（使用 `createI18n`，支持 `localStorage` 持久化）
5. 在 `main.ts` 注册 i18n 插件
6. 在 `AppHeader.vue` 右上角添加语言切换下拉按钮（使用 `GlobalOutlined` 图标）
7. 更新所有 7 个组件文件，将硬编码中文替换为 `$t()` 调用
8. 提交代码

---

## 3. 任务 2：数据库迁移

**AI 执行的操作:**
1. 创建 `docker-compose.yml`（MySQL 8.0 + phpMyAdmin）
2. 创建 `db/init.sql`（建表 + 种子数据）
3. 更新 `pom.xml` 添加 `spring-boot-starter-data-jpa` 和 `mysql-connector-j`
4. 更新 `application.properties` 添加数据源和 JPA 配置
5. 创建 JPA 实体：`Doctor.java`、`Patient.java`
6. 创建 Repository：`DoctorRepository.java`、`PatientRepository.java`
7. 创建 DTO：`DoctorResponse.java`（record）、`LoginRequest.java`、`LoginResponse.java`
8. 创建 Controller：`DoctorController.java`（GET /api/doctors, GET /api/doctors/active, POST /api/doctors/login）
9. 创建 Controller：`PatientController.java`（POST /api/patients/login, POST /api/patients/register）
10. 前端安装 `axios`，创建 `api/index.ts` API 服务层
11. 更新 `store/index.ts` 添加 `fetchDoctorsFromApi()` 和 `loginDoctorApi()` 方法
12. 更新 `App.vue` 在 `onMounted` 中调用 API 获取医生列表
13. 更新 `DoctorLogin.vue` 使用 API 登录
14. 提交代码

---

## 4. 任务 3：患者登录

**AI 执行的操作:**
1. 创建 `docs/PRD_TEMPLATE.md` PRD 模板
2. 创建 `docs/PRD_PatientLogin.md` 患者登录 PRD
3. 创建 `PatientLogin.vue`（支持登录/注册切换）
4. 添加路由 `/patient/login`
5. 在 `AppHeader.vue` 添加患者登录按钮
6. 添加中英文翻译 key
7. 提交代码

---

## 5. 文档编写

**AI 执行的操作:**
1. 创建 `docs/prompts.md`（本文件）
2. 创建 `docs/API_TEST.md` API 测试文档
3. 创建 `docs/TEST_REPORT.md` 测试报告
4. 提交代码

---

## 工具与环境

- **AI 工具**: Claude Code (Claude Opus 4.6)
- **开发环境**: macOS, Node.js, Java 17 (Temurin), Maven
- **前端**: Vue 3 + TypeScript + Vite + Ant Design Vue + vue-i18n + axios
- **后端**: Spring Boot 3.5.7 + JPA + MySQL 8.0
