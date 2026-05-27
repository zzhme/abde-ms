import Vue from 'vue'
import VueRouter from 'vue-router'
import AccountView from "@/views/organization-management/account/AccountView.vue";
import DeptView from "@/views/organization-management/dept/DeptView.vue";
import EmpView from "@/views/organization-management/emp/EmpView.vue";
import HomeView from "@/views/organization-management/home/HomeView.vue";
import BrandView from "@/views/organization-management/brand/BrandView.vue";
import ProductView from "@/views/organization-management/product/ProductView.vue";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/account',
        name: 'account',
        component: AccountView,
        meta: {requiresAuth: true}
    },
    {
        path: '/brand',
        name: 'brand',
        component: BrandView,
        meta: {requiresAuth: true}
    },
    {
        path: '/dept',
        name: 'dept',
        component: DeptView,
        meta: {requiresAuth: true}
    },
    {
        path: '/emp',
        name: 'emp',
        component: EmpView,
        meta: {requiresAuth: true}
    },
    {
        path: '/product',
        name: 'product',
        component: ProductView,
        meta: {requiresAuth: true}
    },
]

const router = new VueRouter({
    routes
})

// 全局前置路由守卫
router.beforeEach((to, from, next) => {
    //先绕过认证,直接跳转,后期学习了认证后,需要把下面的next()删掉,把被注释掉的认证代码取消注释
    //next();
    const token = sessionStorage.getItem('ade_token');

    if (to.meta.requiresAuth && !token) {
        next('/'); // 没有token，跳转到首页（登录页）
    }
    next(); // 不需要登录的页面，直接访问

});
export default router
