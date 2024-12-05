<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
const registerData = ref({
    username: '',
    password: '',
    rePassword: ''
})

//校驗密碼
const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('請再次輸入密碼'))
    } else if (value !== registerData.value.password) {
        callback(new Error('請確保兩次輸入的值一樣'))
    } else {
        callback()
    }
}

//定義表單校驗規則
const rules = {
    username: [
        { required: true, message: '請輸入用戶名', trigger: 'blur' },
        { min: 5, max: 16, message: '長度為5-16位非空字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '請輸入密碼', trigger: 'blur' },
        { min: 5, max: 16, message: '長度為5-16位非空字符', trigger: 'blur' }
    ],
    rePassword: [ //  調用校驗密碼
        { validator: checkRePassword, trigger: 'blur' }
    ]
}

//調用後台接口，完成註冊
import { UserRegisterService, UserLoginService } from '@/api/user'
import { useRouter } from 'vue-router'
const router = useRouter()

const register = async () => {
    let result = await UserRegisterService(registerData.value);
    ElMessage.success(result.message ? result.message : '註冊成功');
}
import useTokenStore from '@/stores/token.js'
const tokenStore = useTokenStore()
const login = async () => {
    let result = await UserLoginService(registerData.value);
    ElMessage.success(result.message ? result.message : '登陸成功');
    tokenStore.setToken(result.data);
    router.push('/');
}
//清空數據模型
const clearRegisterData = () => {
    registerData.value = {
        username: '',
        password: '',
        rePassword: ''
    }
}

</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg">
            <h1 class="bg_h1">文章後台管理系統</h1>
        </el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- 注册表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>註冊</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="請輸入用戶名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="請輸入密碼"
                        v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="請輸入再次密碼"
                        v-model="registerData.rePassword"></el-input>
                </el-form-item>
                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="register">
                        註冊
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false; clearRegisterData()">
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- 登录表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules" status-icon>
                <el-form-item>
                    <h1>登陸</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="請輸入用戶名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="請輸入密碼"
                        v-model="registerData.password" show-password></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>記住我</el-checkbox>
                        <el-link type="primary" :underline="false">忘記密碼</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login" @keyup.enter="login">登陸</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true; clearRegisterData()">
                        註冊 →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background: url('@/assets/login_bg.jpg') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
        display: flex;
        /* 添加此行以启用 Flexbox 布局 */
        justify-content: center;
        /* 水平居中 */
        align-items: center;
        /* 垂直居中 */
        text-align: center;
        /* 作为备选，如果 h1 内只有文本 */
        user-select: none;
        margin: 0;
        /* 移除默认的外边距 */
        padding: 0;
        /* 移除默认的内边距 */
        /* 如果 h1 是直接子元素，下面的 h1 规则可以放置在 .bg 下 */
    }

    .bg_h1 {
        color: #fff;
        margin: 0;
        /* 确保 h1 没有额外的外边距 */
        padding: 0;
        /* 确保 h1 没有额外的内边距 */
        /* 如果 h1 包含文本，以下两行可以确保文本居中 */
        text-align: center;
        font-weight: normal;
        text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
        font-size: 2.5em;
        /* 或任何适合的字体大小 */
        line-height: 1.2;
        letter-spacing: 2px;
        border: 2px solid #fff;
        /* 白色边框 */
        padding: 10px;
        /* 边框内边距 */
        border-radius: 5px;
        /* 边框圆角 */
        color: #feb47b;
        transform: scale(1.05);
    }
    .bg_h1:hover {
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }

            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        animation: fadeIn 1s ease-out;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>