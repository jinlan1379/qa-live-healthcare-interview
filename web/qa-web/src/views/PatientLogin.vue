<template>
  <div class="patient-login">
    <div class="login-container">
      <div class="login-header">
        <h1>{{ isRegister ? $t('patientLogin.registerTitle') : $t('patientLogin.title') }}</h1>
        <p>{{ isRegister ? $t('patientLogin.registerSubtitle') : $t('patientLogin.subtitle') }}</p>
      </div>

      <a-form
        :model="formState"
        :rules="rules"
        @finish="onFinish"
        layout="vertical"
        class="login-form"
      >
        <a-form-item :label="$t('patientLogin.username')" name="username">
          <a-input
            v-model:value="formState.username"
            size="large"
            :placeholder="$t('patientLogin.usernamePlaceholder')"
          >
            <template #prefix>
              <UserOutlined />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item v-if="isRegister" :label="$t('patientLogin.name')" name="name">
          <a-input
            v-model:value="formState.name"
            size="large"
            :placeholder="$t('patientLogin.namePlaceholder')"
          >
            <template #prefix>
              <IdcardOutlined />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item :label="$t('patientLogin.password')" name="password">
          <a-input-password
            v-model:value="formState.password"
            size="large"
            :placeholder="$t('patientLogin.passwordPlaceholder')"
          >
            <template #prefix>
              <LockOutlined />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item>
          <a-button type="primary" html-type="submit" size="large" block :loading="loading">
            {{ isRegister ? $t('patientLogin.register') : $t('patientLogin.login') }}
          </a-button>
        </a-form-item>
      </a-form>

      <div class="switch-mode">
        <span>{{ isRegister ? $t('patientLogin.hasAccount') : $t('patientLogin.noAccount') }}</span>
        <a @click="isRegister = !isRegister">
          {{ isRegister ? $t('patientLogin.goLogin') : $t('patientLogin.goRegister') }}
        </a>
      </div>

      <a-alert
        v-if="!isRegister"
        :message="$t('patientLogin.testAccountTip')"
        :description="$t('patientLogin.testAccountDesc')"
        type="info"
        show-icon
        closable
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { message } from 'ant-design-vue';
import { useI18n } from 'vue-i18n';
import { UserOutlined, LockOutlined, IdcardOutlined } from '@ant-design/icons-vue';
import { patientApi } from '../api';
import { store } from '../store';

const router = useRouter();
const { t } = useI18n();
const loading = ref(false);
const isRegister = ref(false);

const formState = reactive({
  username: '',
  password: '',
  name: '',
});

const rules = computed(() => {
  const base: Record<string, unknown[]> = {
    username: [{ required: true, message: t('patientLogin.usernameRequired') }],
    password: [{ required: true, message: t('patientLogin.passwordRequired') }],
  };
  if (isRegister.value) {
    base.name = [{ required: true, message: t('patientLogin.nameRequired') }];
  }
  return base;
});

const onFinish = async () => {
  loading.value = true;
  try {
    if (isRegister.value) {
      const res = await patientApi.register({
        username: formState.username,
        password: formState.password,
        name: formState.name,
      });
      if (res.data.success) {
        // Auto login after register
        store.verifyPatient(formState.name, '');
        message.success(t('patientLogin.registerSuccess'));
        router.push('/consultation');
      } else {
        message.error(res.data.message);
      }
    } else {
      const res = await patientApi.login(formState.username, formState.password);
      if (res.data.success && res.data.user) {
        const user = res.data.user as { name: string };
        store.verifyPatient(user.name, '');
        message.success(t('patientLogin.loginSuccess'));
        router.push('/consultation');
      } else {
        message.error(t('patientLogin.loginFailed'));
      }
    }
  } catch {
    message.error(t('patientLogin.loginFailed'));
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.patient-login {
  min-height: calc(100vh - 64px);
  padding-top: 64px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-container {
  background: #fff;
  border-radius: 16px;
  padding: 48px;
  width: 100%;
  max-width: 450px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.login-header {
  text-align: center;
  margin-bottom: 40px;
}

.login-header h1 {
  font-size: 32px;
  font-weight: 700;
  color: #333;
  margin-bottom: 8px;
}

.login-header p {
  font-size: 16px;
  color: #666;
}

.login-form {
  margin-bottom: 16px;
}

.switch-mode {
  text-align: center;
  margin-bottom: 24px;
  color: #666;
}

.switch-mode a {
  color: #1890ff;
  cursor: pointer;
  margin-left: 8px;
}

@media (max-width: 768px) {
  .login-container {
    margin: 24px;
    padding: 32px 24px;
  }

  .login-header h1 {
    font-size: 24px;
  }
}
</style>
