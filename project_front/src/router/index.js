/**
 * 路由器模块
 */
import Vue from 'vue'
import Router from 'vue-router'
import maincontent from '@/views/MainContent'
import index from '@/views/index'
import login from '@/views/login'
import personalspace from '@/views/personalspace'
import RightContentList from '@/components/MainContent/RightContent/RightContentList.vue'
import RightContentDetail from '@/components/MainContent/RightContent/RightContentDetail.vue'
import RightContentDetailEdit from '@/components/MainContent/RightContent/RightContentDetailEdit.vue'
import RightContentNewDoc from '@/components/MainContent/RightContent/RightContentNewDoc.vue'
import error from '@/components/error/error.vue'

//引入vue-router
Vue.use(Router)

//向外暴露路由器对象  redirect重定向
const router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: login
        },
        {
            path: '/index',
            component: index,
            meta: {
                requireAuth: true
            }
        },
        {
            path: '/error',
            component: error
        },
        {
            path: '/login',
            component: login
        },
        {
            path: '/personalSpace',
            component: personalspace
        },
        {
            path: '/content',
            component: maincontent,
            children: [
                {
                    path: '/',
                    component: RightContentList
                },
                {
                    path: '/content/create',
                    name: 'create',
                    component: RightContentNewDoc
                },
                {
                    path: '/content/detail/:docId',
                    name: 'detail',
                    component: RightContentDetail
                },
                {
                    path: '/content/edit/:docId',
                    name: 'edit',
                    component: RightContentDetailEdit
                },
                {
                    path: '/content/:productId',
                    component: RightContentList
                },
                {
                    path: '/content/:productId/:deptBelong',
                    component: RightContentList
                },

            ]
        }
    ]
})


//快乐挂载路由导航守卫
router.beforeEach((to, from, next) => {
    if (to.path === '/login' || to.path === '/' || to.path === '/error') {
        next()
        return
    }
    const authorizationStr = window.localStorage.getItem('Authorization')
    if (!authorizationStr) return next('/error')
    next()
})

export default router

// 添加这下面一段代码，可以解决点击侧边栏报错
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
};
