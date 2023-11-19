import { createStore } from 'vuex'
import { LoginParams } from '@/types/LoginParams'
import { User } from '@/types/User'
import { ErrorInfo } from '@/types/ErrorInfo'

export default createStore({
  state: {
    currentUser: null as User | null,
    loginMessage: '' as string
  },
  getters: {
    isLoggedIn(aState) {
      return aState.currentUser != null;
    },
    getLoginMessage(aState): string {
      return aState.loginMessage;
    }
  },
  mutations: {
    setCurrentUser(aState, aUser: User) {
      aState.currentUser = aUser;
      aState.loginMessage = '';
    },
    setLoginError(aState, aErrorMessage: string) {
      aState.currentUser = null;
      aState.loginMessage = aErrorMessage;
    }
  },
  actions: {
    login(aContext, aParams: LoginParams) {
      const api = "http://" + window.location.host + "/holiday-calculator-web-service/login/" + aParams.login + "/" + aParams.password;
      fetch(api)
        .then(response => {
          if (response.ok) {
            return response.json();
          }
          return response.json().then((info: ErrorInfo) => {
            let message = info.code == 401 ? "Неверный логин или пароль." : info.description;
            throw new Error(message);
          });
        })
        .then((user: User) => {
          console.log("loginResponseJSON" + user.firstName);
          aContext.commit('setCurrentUser', user);
        })
        .catch(error => {
          console.log("loginError " + error.message);
          aContext.commit('setLoginError', error.message);
        })
    },

    checkAuthentication(aContext) {
      const api = "http://" + window.location.host + "/holiday-calculator-web-service/user/";
      fetch(api)
        .then(response => {
          if (response.ok) {
            return response.json();
          }
          throw new Error(response.statusText);
        })
        .then((user: User) => aContext.commit('setCurrentUser', user))
        .catch(error => aContext.commit('setLoginError', ""))
    }
  },
  modules: {
  }
})
