<script setup>
import { ref } from 'vue'
//校驗密碼
const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('請再次輸入密碼'))
    } else if (value !== userInfo.value.NewPassword) {
        callback(new Error('請確保兩次輸入的值一樣'))
    } else {
        callback()
    }
}
//定義表單校驗規則
const passwordRules = {
    OriPassword: [
        { required: true, message: '請輸入原密碼', trigger: 'blur' },
        { min: 5, max: 16, message: '長度為5-16位非空字符', trigger: 'blur' }
    ],
    NewPassword: [
        { required: true, message: '請輸入新密碼', trigger: 'blur' },
        { min: 5, max: 16, message: '長度為5-16位非空字符', trigger: 'blur' }
    ],
    rePassword: [
        { required: true, validator: checkRePassword, trigger: 'blur' }
    ]
}
const userInfo = ref({
    OriPassword: '',
    NewPassword: '',
    rePassword: ''
})

import { UserUpdatePasswordService } from '@/api/user.js'
import { ElMessage } from 'element-plus'
const updatePassword = async () => {
    try {
        const result = await UserUpdatePasswordService({
            old_pwd: userInfo.value.OriPassword,
            new_pwd: userInfo.value.NewPassword,
            re_pwd: userInfo.value.rePassword
        });
        ElMessage.success(result.message ? result.message : '密碼更新成功');
    } catch (error) {
        ElMessage.error('密碼更新失敗');
        console.error('更新密碼時發生錯誤:', error);
    }
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>修改密碼</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo" :rules="passwordRules" label-width="130px" size="large">
                    <el-form-item label="原密碼" prop="OriPassword">
                        <el-input v-model="userInfo.OriPassword"></el-input>
                    </el-form-item>
                    <el-form-item label="新密碼" prop="NewPassword">
                        <el-input v-model="userInfo.NewPassword"></el-input>
                    </el-form-item>
                    <el-form-item label="再次輸入新密碼" prop="rePassword">
                        <el-input v-model="userInfo.rePassword"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updatePassword">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>