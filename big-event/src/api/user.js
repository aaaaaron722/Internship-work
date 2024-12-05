//導入request.js請求工具
import request from '@/utils/request'
export const UserRegisterService = (registerData)=>{
    //借助於UrlSearchParams完成傳遞
    const params = new URLSearchParams();
    for(let key in registerData){
        params.append(key, registerData[key]);
    }
    return request.post('user/register', params);

}
//登陸
export const UserLoginService = (loginData)=>{
    const params = new URLSearchParams();
    for(let key in loginData){
        params.append(key, loginData[key]);
    }
    return request.post('user/login', params);
}
//獲取用戶詳細信息
export const UserInfoService = () => {
    return request.get('/user/userInfo')
}
//更新用戶詳細信息
export const UserUpdateInfo = (userInfo)=> {
    return request.put('/user/update', userInfo)
}
//修改頭像
export const userAvatarUpdateService = (avatarUrl)=> {
    const params = new URLSearchParams()
    params.append('avatarUrl', avatarUrl)
    return request.patch('/user/updateAvatar', params)
}
//更新密碼
export const UserUpdatePasswordService = (password)=> {
    return request.patch('/user/updatePwd', password)
}