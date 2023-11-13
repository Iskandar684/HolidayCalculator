import { createStore } from 'vuex'
import { LoginParams } from '@/types/LoginParams'

export default createStore({
  state: {
    loggedIn: false
  },
  getters: {
    isLoggedIn(state) {
      return state.loggedIn
    },
  },
  mutations: {
    login(state, loggedIn: boolean) {
      state.loggedIn = loggedIn;
    }
  },
  actions: {
    login(context: any, params: LoginParams) {
      console.log("login " + params.login + "  password " + params.password);
      context.commit('login', true)
    }
  },
  modules: {
  }
})
