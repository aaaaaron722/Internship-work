<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { onMounted, ref } from 'vue'

//文章分类数据模型
const categorys = ref([
    {
        "id": '',
        "categoryName": '',
        "categoryAlias": '',
        "createTime": '',
        "updateTime": ''
    }
])

//用户搜索时选中的分类id
const categoryId = ref('')

//用户搜索时选中的发布状态
const state = ref('')

//文章列表数据模型
const articles = ref([
    {
        "id": 5,
        "title": "陕西旅游攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },
    {
        "id": 5,
        "title": "陕西旅游攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },
    {
        "id": 5,
        "title": "陕西旅游攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },
])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(5)//每页条数
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    articleList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    articleList()
}

//回顯文章分類
import { SearchArticleCategoryService, ArticleListService, AddArticleService, deleteArticleService, ArticleEditService } from '@/api/article.js'
const articleCategoryList = async () => {
    let result = await SearchArticleCategoryService();
    categorys.value = result.data
}

const articleList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        categoryId: categoryId.value ? categoryId.value : null,
        state: state.value ? state.value : null
    }
    let result = await ArticleListService(params)
    //渲染視圖層
    total.value = result.data.total;
    articles.value = result.data.items
    //處理數據，給數據模型擴展一個屬性categoryName，分類名稱
    for (let i = 0; i < articles.value.length; i++) {
        let article = articles.value[i]
        for (let j = 0; j < categorys.value.length; j++) {
            if (article.categoryId == categorys.value[j].id) {
                article.categoryName = categorys.value[j].categoryName
            }
        }
    }
}
articleCategoryList()
articleList()

import { Plus } from '@element-plus/icons-vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const articleModel = ref({
    title: '',
    categoryId: '',
    coverImg: '',
    coverImg: '',
    state: ''
})
//導入token
import useTokenStore from '@/stores/token.js'
import { ElMessage, ElMessageBox } from 'element-plus'
const tokenStore = useTokenStore()
//上傳成功的回調函數
const uploadSuccess = (result) => {
    articleModel.value.coverImg = result.data
    console.log(result.data);
}
const clearArticleModel = () => {
    articleModel.value.title = ''
    articleModel.value.categoryId = ''
    articleModel.value.coverImg = ''
    articleModel.value.coverImg = ''
    articleModel.value.state = ''
}
const AddArticle = async (clickState) => {
    articleModel.value.state = clickState
    let result = await AddArticleService(articleModel.value)
    ElMessage.success(result.message ? result.message : '添加成功')
    visibleDrawer.value = false
    articleList()
    clearArticleModel()
}
const deleteArticle = (row) => {
    ElMessageBox.confirm(
        '文章將會被刪除，確定嗎？',
        '提示',
        {
            confirmButtonText: '確認',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            let result = await deleteArticleService(row.id)
            ElMessage({
                type: 'success',
                message: '操作成功',
            })
            articleList()
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '已取消',
            })
        })
}
const title = ref('')
//展示編輯彈窗
const showDialog = (row) => {
    visibleDrawer.value = true
    title.value = '編輯文章'
    //數據拷貝
    articleModel.value.title = row.title
    articleModel.value.categoryId = row.categoryId
    /* articleModel.value.coverImg = row.coverImg */
    articleModel.value.content = row.content
    articleModel.value.state = '已發布'
    articleModel.value.id = row.id
}
const updateArticle = async () => {
    let result = await ArticleEditService(articleModel.value)
    ElMessage.success(result.message ? result.message : '編輯成功')
    articleList()
    visibleDrawer.value = false
}
//是否禁用草稿按鈕
const scriptAbled = ref(false)
const isAbled = () => {
    if (title.value === '編輯文章') {
        return scriptAbled.value = true
    }
}
const ArticleContent = () => {
    visibleDrawer.value = true
}
//不加onMounted的話會有bug，就是vue只加載數據模型，並沒有調用函數
onMounted(() => {
    articleList()
})
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>文章管理</span>
                <div class="extra">
                    <el-button type="primary"
                        @click="visibleDrawer = true; title = '添加文章'; clearArticleModel()">添加文章</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="文章分類">
                <el-select placeholder="請選擇" v-model="categoryId" size="large" style="width: 240px">
                    <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="發布狀態：">
                <el-select placeholder="請選擇" v-model="state" size="large" style="width: 240px">
                    <el-option label="已發布" value="已發布"></el-option>
                    <el-option label="草稿" value="草稿"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="articleList">搜索</el-button>
                <el-button @click="categoryId = ''; state = ''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 文章列表 -->
        <el-table :data="articles" style="width: 100%" @click="ArticleContent">
            <el-table-column label="文章標題" width="400" prop="title"></el-table-column>
            <el-table-column label="分類" prop="categoryName"></el-table-column>
            <el-table-column label="發表時間" prop="createTime"> </el-table-column>
            <el-table-column label="狀態" prop="state"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteArticle(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="沒有數據" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
    </el-card>
    <!-- 抽屉 -->
    <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="45%">
        <!-- 添加文章表单 -->
        <el-form :model="articleModel" label-width="100px">
            <el-form-item label="文章標題">
                <el-input v-model="articleModel.title" placeholder="請輸入標題"></el-input>
            </el-form-item>
            <el-form-item label="文章分類">
                <el-select placeholder="請選擇" v-model="articleModel.categoryId" style="width: 50%;">
                    <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="文章封面">
                <!-- 
                auto-upload:設置是否自動上傳
                action:設置服務器接口路徑 
                name:文件字段名
                headers:上傳的請求頭
                on-success:上傳成功的回調函數
                -->
                <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false" action="/api/upload"
                    name="file" :headers="{ 'Authorization': tokenStore.token }" :on-success="uploadSuccess">
                    <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon">
                        <Plus />
                    </el-icon>
                </el-upload>
            </el-form-item>
            <el-form-item label="文章内容">
                <div class="editor"><quill-editor theme="snow" v-model:content="articleModel.content"
                        contentType="html">
                    </quill-editor></div>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="title == '編輯文章' ? updateArticle() : AddArticle('已發布')">發布</el-button>
                <el-button type="info" @click="AddArticle('草稿')" :disabled="isAbled()">草稿</el-button>
                <el-button @click="visibleDrawer = false">取消</el-button>
            </el-form-item>
        </el-form>
    </el-drawer>

</template>
<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

/* 抽屉样式 */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}
</style>