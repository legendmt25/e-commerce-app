import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

import VNetworkGraph from 'v-network-graph';
import 'v-network-graph/lib/style.css';

import './input.css';

createApp(App).use(VNetworkGraph).use(router).mount('#app');
