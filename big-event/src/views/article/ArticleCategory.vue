<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
const categorys = ref([
    {
        "id": 3,
        "categoryName": "美食",
        "categoryAlias": "my",
        "createTime": "2023-09-02 12:06:59",
        "updateTime": "2023-09-02 12:06:59"
    },
    {
        "id": 4,
        "categoryName": "娱乐",
        "categoryAlias": "yl",
        "createTime": "2023-09-02 12:08:16",
        "updateTime": "2023-09-02 12:08:16"
    },
    {
        "id": 5,
        "categoryName": "军事",
        "categoryAlias": "js",
        "createTime": "2023-09-02 12:08:33",
        "updateTime": "2023-09-02 12:08:33"
    }
])
//列出文章分類
import { SearchArticleCategoryService, ArticleAddCategoryService, ArticleEditCategoryService , ArticleDeleteCategoryService} from '@/api/article.js'

const articleCategoryList = async () => {
    let result = await SearchArticleCategoryService();
    categorys.value = result.data
}
articleCategoryList();
//控制添加分类弹窗
const dialogVisible = ref(false)

//添加分类数据模型
const categoryModel = ref({
    categoryName: '',
    categoryAlias: ''
})
//添加分类表单校验
const rules = {
    categoryName: [
        { required: true, message: '請輸入分類名稱', trigger: 'blur' },
    ],
    categoryAlias: [
        { required: true, message: '清輸入分類別名', trigger: 'blur' },
    ]
}
import {ElMessage , ElMessageBox} from 'element-plus'
//新增分類
const addCategory = async()=>{
    let result = await ArticleAddCategoryService(categoryModel.value)
    ElMessage.success(result.message ? result.message : '添加成功')
    articleCategoryList()
    dialogVisible.value= false
}
//定義變量控制標題的展示
const title = ref('')
//展示編輯彈窗
const showDialog = (row)=>{
    dialogVisible.value=true; title.value='編輯分類'
    //數據拷貝
    categoryModel.value.categoryName = row.categoryName
    categoryModel.value.categoryAlias = row.categoryAlias
    categoryModel.value.id = row.id
}
const updateCategory = async()=>{
    let result = await ArticleEditCategoryService(categoryModel.value)
    ElMessage.success(result.message ? result.message : '編輯成功')
    articleCategoryList()
    dialogVisible.value= false
}
const clearData = ()=>{
    categoryModel.value.categoryName = ''
    categoryModel.value.categoryAlias = ''
}
const deleteCategory = (row)=>{
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
      let result = await ArticleDeleteCategoryService(row.id)
      ElMessage({
        type: 'success',
        message: '操作成功',
      })
      articleCategoryList()
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '已取消',
      })
    })
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>文章分類</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible=true; title='添加分類'; clearData()">添加分類</el-button>
                </div>
            </div>
        </template>
        <el-table :data="categorys" style="width: 100%">
            <el-table-column label="序號" width="100" type="index"> </el-table-column>
            <el-table-column label="分類名稱" prop="categoryName"></el-table-column>
            <el-table-column label="分類別名" prop="categoryAlias"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCategory(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="沒有數據" />
            </template>
        </el-table>
    </el-card>
    <!-- 添加分类弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
        <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
            <el-form-item label="分類名稱" prop="categoryName">
                <el-input v-model="categoryModel.categoryName" minlength="1" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item label="分類別名" prop="categoryAlias">
                <el-input v-model="categoryModel.categoryAlias" minlength="1" maxlength="15"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="title=='添加分類'?addCategory():updateCategory()"> 確認 </el-button>
            </span>
        </template>
    </el-dialog>
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
</style>