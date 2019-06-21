import Vue from 'vue'
import App from './App.vue'

import vueSocket from "vue-socket.io";
import Echarts from 'echarts'

Vue.use(new vueSocket({
  connection:"http://127.0.0.1:3000/"
}));

Vue.prototype.echarts = Echarts
Vue.use(Echarts);


Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
