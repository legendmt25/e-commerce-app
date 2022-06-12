import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('@/views/HomeView.vue'),
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('@/views/AboutView.vue'),
  },
  {
    path: '/contact',
    name: 'contact',
    component: () => import('@/views/ContactView.vue'),
  },
  {
    path: '/21fit',
    name: '21fit',
    component: () => import('@/views/21FitView.vue'),
  },
  {
    path: '/products',
    name: 'productsList',
    component: () => import('@/views/ProductsListView.vue'),
  },
  {
    path: '/products/create',
    name: 'productCreate',
    component: () => import('@/views/ProductCreateView.vue'),
  },
  {
    path: '/products/:id/add-attribute',
    name: 'productAddAttribute',
    component: () => import('@/views/ProductManageAttributeView.vue'),
  },
  {
    path: '/products/:id',
    name: 'productDetails',
    component: () => import('@/views/ProductDetailsView.vue'),
  },
  {
    path: '/products/:id/configure-attributes',
    name: 'configureAttributes',
    component: () => import('@/views/ConfigureProductAttributesView.vue'),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
