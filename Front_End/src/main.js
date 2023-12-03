import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

// Boostrap 5.2.3 import
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap'

// FontAwesome 관련 import
import { library } from '@fortawesome/fontawesome-svg-core'
import { faHeart as fasHeart } from '@fortawesome/free-solid-svg-icons'
import { faHeart as farHeart } from '@fortawesome/free-regular-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

// 소켓 추가
import socket from 'vue3-websocket'

// 아이콘 라이브러리에 추가
library.add(fasHeart, farHeart)

const app = createApp(App)

app.use(createPinia())
app.use(router)

// FontAwesomeIcon 컴포넌트 전역 등록
app.component('font-awesome-icon', FontAwesomeIcon)

app.use(socket, 'ws://192.168.203.121:8080/ws/chat')

app.mount('#app')
