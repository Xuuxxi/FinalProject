import $ from 'jquery'

export default{
  state: {
    id: '',
    username: '',
    photo: '',
    token: '',
    is_login: false
  },
  getters: {
  },
  //同步函数放这边
  mutations: {
    updateUser(state, user){
      state.id = user.id;
      state.username = user.username;
      state.photo =  user.photo;
      state.is_login = user.is_login;
    },
    updateToken(state,token){
      state.token = token;
    },
    logout(state){
      state.id = '';
      state.username = '';
      state.photo = '';
      state.is_login = false;
      state.token = '';
    },
    updateUserPhoto(state, photo){
      state.photo = photo;
    }
  },
  //异步函数放这里
  actions: {
    login(context, data){
      $.ajax({
        url: 'http://localhost:3000/user/account/login/',
        type: 'post',
        data: {
          username: data.username,
          password: data.password
        },
        success(resp){
          if(resp.error_message === "success"){
            localStorage.setItem('jwt_token',resp.token);
            context.commit("updateToken",resp.token);
            data.success(resp);
          }else{
            data.error(resp);
          }
        },
        error(resp){
          data.error(resp);
        }
      })
    },
    getInfo(context,data){
      $.ajax({
        url: 'http://localhost:3000/user/account/info/',
        type:'get',
        headers: {
          Authorization: 'Bearer ' + context.state.token
        },
        success(resp){
          if(resp.error_message === "success"){
            context.commit("updateUser",{
              ...resp,
              is_login: true
            });
            data.success(resp);
          }else{
            data.error(resp);
          }
        },
        error(resp){
          data.error(resp);
        }
      })
    },
    logout(context){
      localStorage.removeItem('jwt_token')
      context.commit("logout")
    }
  },
  modules: {
  }
}
