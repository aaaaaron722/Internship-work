<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom,
    Search,
} from '@element-plus/icons-vue'
import avatar from '@/assets/Logo去背.png'
import { ref } from 'vue'
const shadowGroup = ref({
    name: 'Dark Shadow',
    type: 'lighter',
})

const getCssVarName = (type) => {
    return `--el-box-shadow${type ? '-' : ''}${type}`
}
//獲取用戶詳細信息
import { UserInfoService } from '@/api/user.js'
import UserInfoStore from '@/stores/userInfo.js'
import useTokenStore from '@/stores/token.js'
const tokenStore = useTokenStore()

const userInfoStore = UserInfoStore()
const getUserInfo = async () => {
    let result = await UserInfoService()
    userInfoStore.setInfo(result.data)
}
getUserInfo()
import { useRouter } from 'vue-router'
import { ElMessageBox, ElMessage } from 'element-plus'
const router = useRouter()
const handleCommand = (command) => {
    if (command === 'logout') {
        ElMessageBox.confirm(
            '您確認要登出嗎？',
            '提示',
            {
                confirmButtonText: '確認',
                cancelButtonText: '取消',
                type: 'warning',
            }
        )
            .then(async () => {
                tokenStore.removeToken()
                userInfoStore.removeInfo()
                router.push('/login')
                ElMessage({
                    type: 'success',
                    message: '登出成功',
                })
            })
            .catch(() => {
                ElMessage({
                    type: 'info',
                    message: '已取消',
                })
            })
    } else {
        router.push('/user/' + command)
    }
}

const categorys = ref([
    {
        "id": 3,
        "categoryName": "美食",
        "categoryAlias": "my",
        "createTime": "2023-09-02 12:06:59",
        "updateTime": "2023-09-02 12:06:59"
    }
])

const articles = ref([
    {
        "id": 10,
        "title": "北京修改過了",
        "content": "<p>北京旅遊</p>",
        "coverImg": "",
        "state": "草稿",
        "categoryId": 3,
        "createTime": "2024-08-20 10:13:32",
        "updateTime": "2024-08-20 10:13:33"
    }
])
//抽屜開關
const visibleDrawer = ref(false)
const showDialog = () => {
    visibleDrawer.value = true
}
//分類列表
import { SearchArticleCategoryService, SearchArticleContentService } from '@/api/article'
const ArticleCategoryList = async () => {
    let result = await SearchArticleCategoryService()
    categorys.value = result.data
}
ArticleCategoryList()
const word = ref('')
//搜尋內容
const SearchContent = async () => {
    if (word.value) {
        let result = await SearchArticleContentService(word.value)
        articles.value = result.data
        //處理數據把articleId轉成categoryName
        for (let i = 0; i < articles.value.length; i++) {
            let article = articles.value[i]
            for (let j = 0; j < categorys.value.length; j++) {
                if (article.categoryId == categorys.value[j].id) {
                    article.categoryName = categorys.value[j].categoryName
                }
            }
        }
        showDialog()
    } else {
        ElMessage.error('搜索文章時發生錯誤')
    }
}
</script>

<template>
    <el-container class="layout-container">
        <!-- 左侧菜单 -->
        <el-aside width="200px" :style="{ boxShadow: `var(${getCssVarName(shadowGroup.type)})` }">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#ffd04b" background-color="#232323" text-color="#fff" router>
                <el-menu-item index="/article/category">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>文章分類</span>
                </el-menu-item>
                <el-menu-item index="/article/manage">
                    <el-icon>
                        <Promotion />
                    </el-icon>
                    <span>文章管理</span>
                </el-menu-item>
                <el-sub-menu>
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>個人中心</span>
                    </template>
                    <el-menu-item index="/user/info">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>基本資料</span>
                    </el-menu-item>
                    <el-menu-item index="/user/avatar">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>更換頭像</span>
                    </el-menu-item>
                    <el-menu-item index="/user/resetPassword">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>重置密碼</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 抽屜 -->
            <el-drawer v-model="visibleDrawer" direction="rtl" size="40%" :with-header="false">
                <h1>快速查找</h1>
                <el-table :data="articles" style="width: auto">
                    <el-table-column label="文章標題" prop="title"></el-table-column>
                    <el-table-column label="分類" prop="categoryName"></el-table-column>
                    <el-table-column label="發表時間" prop="createTime"> </el-table-column>
                    <el-table-column label="狀態" prop="state"></el-table-column>
                    <template #empty>
                        <el-empty description="沒有數據" />
                    </template>
                </el-table>
            </el-drawer>
            <!-- 头部区域 -->
            <el-header :style="{ boxShadow: `var(${getCssVarName(shadowGroup.type)})` }">
                <div>管理員: <strong>{{ userInfoStore.info.nickname }}</strong></div>
                <el-input v-model="word" style="max-width: 600px" placeholder="輸入關鍵字" class="input-with-select"
                    @keyup.enter="SearchContent">
                    <template #prepend>
                        <span>快速搜尋文章關鍵字</span>
                    </template>
                    <template #append>
                        <el-button :icon="Search" @click="SearchContent" />
                    </template>
                </el-input>
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :size="50" style="border: 1px Dashed black;">
                            <img :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar" />
                        </el-avatar>
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info" :icon="User">基本資料</el-dropdown-item>
                            <el-dropdown-item command="avatar" :icon="Crop">更換頭像</el-dropdown-item>
                            <el-dropdown-item command="resetPassword" :icon="EditPen">重置密碼</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登陸</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <router-view></router-view>
            </el-main>
            <!-- 底部区域 -->
            <el-footer>Article Management©2024 Created by 林佑勳</el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #fff;
        box-shadow: --el-box-shadow-dark;

        &__logo {
            height: 120px;
            background: url('@/assets/圖加字去背.png') no-repeat center / 450px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-drawer {
        h1 {
            margin-bottom: 0px;
        }

        .el-table-column {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .icon-group {
            display: flex;
            width: 70px;
            height: auto;
            margin-right: 8px;
        }

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }

    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }


}
</style>