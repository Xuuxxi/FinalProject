import { createStore } from 'vuex'
import ModuleUser from './user'
import ModulPk from './pk'

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: ModuleUser,
    pk: ModulPk
  }
})
