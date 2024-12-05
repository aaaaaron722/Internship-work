import {createRouter, createWebHistory} from 'vue-router'
//導入組件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import ArticleCategoryVue from '@/views/article/ArticleCategory.vue'
import ArticleManageVue from '@/views/article/ArticleManage.vue'
import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'
//定義路由關係
const routes = [
    {path:'/login', component:LoginVue},
    {
        path:'/', 
        component:LayoutVue,
        //重定向
        redirect:'/article/manage',
        //子路由
        children:[
            {path:'/article/category', component: ArticleCategoryVue},
            {path:'/article/manage', component: ArticleManageVue},
            {path:'/user/avatar', component: UserAvatarVue},
            {path:'/user/info', component: UserInfoVue},
            {path:'/user/resetPassword', component: UserResetPasswordVue}
        ]
    }
]
const router = createRouter({
    history:createWebHistory(),
    routes:routes
})

export default router