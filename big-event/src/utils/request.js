//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import { ElMessage } from 'element-plus'
import  useTokenStore  from '@/stores/token.js';
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = '/api';
const instance = axios.create({baseURL})

//添加請求攔截器
instance.interceptors.request.use(
    (config)=>{
        const tokenStore = useTokenStore();
        if(tokenStore.token){
            config.headers.Authorization = tokenStore.token
        }
        return config;
    },
    (err)=>{
        Promise.reject(err)
    }
)
import router from '@/router'
//添加響應攔截器
instance.interceptors.response.use(
    result=>{
        if(result.data.code === 0){
            return result.data;
        }
        ElMessage.error(result.data.message? result.data.message : '服務異常')
        return Promise.reject(result.data)
    },
    err=>{
        //判斷響應狀態碼，如果為401，證明未登陸，提示請登陸，並跳轉到登陸
        if(err.response.status === 401){
            ElMessage.error('請先登陸')
            router.push('/login')
        }else {
            ElMessage.error('服務異常')
        }
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;