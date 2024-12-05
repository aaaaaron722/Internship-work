import request from '@/utils/request'
export const SearchArticleCategoryService = () => {
    return request.get('/category');
}
export const ArticleAddCategoryService = (categoryData) => {
    return request.post('/category', categoryData);
}
export const ArticleEditCategoryService = (categoryData) => {
    return request.put('/category', categoryData);
}
export const ArticleDeleteCategoryService = (id) => {
    return request.delete('/category?id='+ id)
}

export const ArticleListService = (params)=>{
    return request.get('/article', {params:params})
}
export const AddArticleService = (articleData) => {
    return request.post('/article', articleData)
}
export const deleteArticleService = (id) => {
    return request.delete('/article?id='+ id)
}
export const ArticleEditService = (articleData) => {
    return request.put('/article', articleData)
}
export const SearchArticleContentService = (word)=>{
    return request.get('/article/keyword?word=' + word)
}