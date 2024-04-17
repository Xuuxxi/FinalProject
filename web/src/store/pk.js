export default {
  state: {
    status: "matching",  // matching表示匹配界面，playing表示对战界面
    socket: null,
    opponent_username: "",
    opponent_photo: "",
    loser: "none",  // none、all、A、B
    a_id: '',
    b_id: '',
    a_rating: '',
    b_rating: '',
    aMap: [],
    bMap: [],
    step: 0,
    dice_num: 0,
    a_score: 0,
    b_score: 0,

    gameObject: null,
    gameMap: null,
    a_sx: 0,
    a_sy: 0,
    b_sx: 0,
    b_sy: 0
  },
  getters: {
  },
  //同步函数放这边
  mutations: {
    updateAS(state,score){
      state.a_score = score;
    },
    updateBS(state,score){
      state.b_score = score;
    },
    updateSocket(state, socket) {
      state.socket = socket;
    },
    updateOpponent(state, opponent) {
      state.opponent_username = opponent.username;
      state.opponent_photo = opponent.photo;
    },
    updateStatus(state, status) {
      state.status = status;
    },
    updateLoser(state, loser) {
      state.loser = loser;
    },
    updateGame(state, game) {
      state.aMap = game.aMap;
      state.bMap = game.bMap;
      state.a_id = game.a_id;
      state.b_id = game.b_id;
      state.a_rating = game.a_rating;
      state.b_rating = game.b_rating;
      state.step = game.step;
      state.dice_num = game.dice_num;
    },
    updateRoll(state, MyData){
      state.dice_num = MyData.dice_num;
    },
    updateCurMap(state, MyData){
      state.step = MyData.step;
      state.aMap = MyData.aMap;
      state.bMap = MyData.bMap;
      state.dice_num = MyData.dice_num;
    },
    updateRes(state,MyData){
      state.loser = MyData.loser;
      state.a_score = MyData.a_score;
      state.b_score = MyData.b_score;
    },
    updateSnakeGame(state, game) {
      state.gameMap = game.map;
      state.a_id = game.a_id;
      state.a_sx = game.a_sx;
      state.a_sy = game.a_sy;
      state.b_id = game.b_id;
      state.b_sx = game.b_sx;
      state.b_sy = game.b_sy;
    },
    updateGameObject(state, gameObject) {
      state.gameObject = gameObject;
    }
  },
  //异步函数放这里
  actions: {
  },
  modules: {
  }
}
