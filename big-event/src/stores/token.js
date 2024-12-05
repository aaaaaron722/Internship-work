import {defineStore} from 'pinia'
import {ref} from 'vue'
/* 
第一個參數：名字，唯一性
第二個參數：函數，函數內部可以定義狀態的所有內容
返回值：函數
 */
const useTokenStore = defineStore('token', ()=>{
    const token = ref('')
    const setToken = (newToken)=>{
        token.value = newToken
    }
    const removeToken = ()=>{
        token.value = ''
    }
    return {token, setToken, removeToken}
},{
    persist:true
});
export default useTokenStore;