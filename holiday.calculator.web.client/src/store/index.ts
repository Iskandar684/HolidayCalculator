import { createStore } from 'vuex'
import { LoginParams } from '@/types/LoginParams'
import { User } from '@/types/User'
import { ErrorInfo } from '@/types/ErrorInfo'

export default createStore({
  state: {
    loggedIn: false,
    currentUser: {} as User
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
      const api = "http://" + window.location.host + "/holiday-calculator-web-service/login/" + params.login + "/" + params.password;
      console.log("loginURL " + api)
      fetch(api)
        .then(response => {
          if (response.ok) {
            return response.json();
          }
          return response.json().then((info: ErrorInfo) => {
            throw new Error(info.description);
          });
        })
        .then((user: User) => {
          console.log("loginResponseJSON" + user.firstName);
          this.state.currentUser = user;
          context.commit('login', true);
        })
        .catch(error => console.log("loginError " + error.message))
    }
  },
  modules: {
  }
})
