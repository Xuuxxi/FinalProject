<template>
  <div class="row">
    <div class="col-1">
      <div class="Info">
        <div v-if="role === 'A'">
          <img :src="$store.state.user.photo" alt="">
          <button v-if="playByBot === 0" type="button" class="btn btn-success"
            style="width: 100%; margin-top: 9px; border-radius: 10px;" @click="startBot">托管</button>
          <button v-else type="button" class="btn btn-success"
            style="width: 100%; margin-top: 9px; border-radius: 10px;" @click="stopBot">取消</button>
        </div>
        <div v-else><img :src="$store.state.pk.opponent_photo" alt=""></div>
      </div>
    </div>
    <div class="col-4">
      <div class="lInfo">
        <div v-if="role === 'A'">
          <div class="username">{{$store.state.user.username}}</div>
        </div>
        <div v-else>
          <div class="username">{{$store.state.pk.opponent_username}}</div>
        </div>
        <div class="rating">{{$store.state.pk.a_rating}}</div>
      </div>
    </div>
    <div class="col-2">
      <div class="diceGround">
        <!-- <button @click="roll">
          <div v-if="$store.state.pk.dice_num === 0">投掷</div>
          <div v-else>{{$store.state.pk.dice_num}}</div>
        </button> -->
        <input type="checkbox" id="dice" @click="roll" :checked="$store.state.pk.dice_num">

        <label for="dice">
          <div
            :class="$store.state.pk.dice_num === 1 ? 'dice1' : ($store.state.pk.dice_num === 2 ? 'dice2' : (($store.state.pk.dice_num === 3 ? 'dice3' : ($store.state.pk.dice_num === 4 ? 'dice4' : ($store.state.pk.dice_num === 5 ? 'dice5' : ($store.state.pk.dice_num === 6 ? 'dice6' : 'dice'))))))">
          </div>
        </label>
      </div>
      <div class="badge bg-primary text-wrap" style="width: 100%;margin: 0 auto;">
        <div v-if="$store.state.pk.step === 0">现在是A回合</div>
        <div v-else>现在是B回合</div>
      </div>
    </div>
    <div class="col-4">
      <div class="rInfo">
        <div v-if="role === 'B'">
          <div class="Rusername">{{$store.state.user.username}}</div>
        </div>
        <div v-else>
          <div class="Rusername">{{$store.state.pk.opponent_username}}</div>
        </div>
        <div class="Rrating">{{$store.state.pk.b_rating}}</div>
      </div>
    </div>
    <div class="col-1">
      <div class="Info">
        <div v-if="role === 'B'">
          <img :src="$store.state.user.photo" alt="">
          <button v-if="playByBot === 0" type="button" class="btn btn-success"
            style="width: 100%; margin-top: 9px; border-radius: 10px;" @click="startBot">托管</button>
          <button v-else type="button" class="btn btn-success"
            style="width: 100%; margin-top: 9px; border-radius: 10px;" @click="stopBot">取消</button>
        </div>
        <div v-else><img :src="$store.state.pk.opponent_photo" alt=""></div>
      </div>
    </div>
    <div class="col-6">
      <div class="group_but">
        <div class="role">A GROUND</div>
        <button v-for="(item,index) in $store.state.pk.aMap" :key="index" :value="index" @click="putNum(0,index)"
          :style="'background: url(' + dice_pic[item] + ') no-repeat;'">
        </button>
      </div>
    </div>
    <div class="col-6">
      <div class="group_but">
        <div class="role">B GROUND</div>
        <button v-for="(item,index) in $store.state.pk.bMap" :key="index" :value="index" @click="putNum(1,index)"
          :style="'background: url(' + dice_pic[item] + ') no-repeat;'"></button>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { ref } from 'vue';
import '@/assets/css/DiceCss.css'

export default {
  setup() {
    const store = useStore();
    const socket = store.state.pk.socket;
    let role = ref('');
    let dice_pic = ref([
      'https://pic1.imgdb.cn/item/6336928816f2c2beb15f2b09.png',
      'https://pic1.imgdb.cn/item/63368a3216f2c2beb15612c9.png',
      'https://pic1.imgdb.cn/item/63368a3216f2c2beb15612d0.png',
      'https://pic1.imgdb.cn/item/63368a3216f2c2beb15612c3.png',
      'https://pic1.imgdb.cn/item/63368a3516f2c2beb1561793.png',
      'https://pic1.imgdb.cn/item/63368a3516f2c2beb15617a2.png',
      'https://pic1.imgdb.cn/item/63368a3516f2c2beb15617b1.png'
    ]);
    let playByBot = ref(0);

    if (store.state.pk.a_id == store.state.user.id) role.value = "A";
    else role.value = "B";

    const roll = () => {
      if (((store.state.user.id == store.state.pk.a_id) && (store.state.pk.step == 0)) || ((store.state.user.id == store.state.pk.b_id) && (store.state.pk.step == 1))) {
        if (store.state.pk.dice_num === 0) {
          socket.send(JSON.stringify({
            event: "roll"
          }));
        }
      } else {
        alert("还没到你的回合！");
      }
    }

    const check_valid = (f, pos) => {
      let msg = 'success';
      if (store.state.pk.step === 0 && f === 0) {
        if (store.state.pk.aMap[pos] === 0) {
          return msg;
        } else msg = '只能在数字 0 区域放置';
      } else if (store.state.pk.step === 0 && f === 1) msg = "请操作左侧九宫格";

      if (store.state.pk.step === 1 && f === 1) {
        if (store.state.pk.bMap[pos] != 0) msg = '只能在数字 0 区域放置';
      } else if (store.state.pk.step === 1 && f === 0) msg = "请操作右侧侧九宫格";

      return msg;
    }

    const putNum = (f, pos) => {
      if (store.state.pk.dice_num === 0) {
        alert('请先投掷骰子');
        return;
      }
      let res = check_valid(f, pos)
      if (res != 'success') alert(res);
      else {
        socket.send(JSON.stringify({
          event: "move",
          direction: pos
        }));
      }
    }

    const startBot = () => {
      playByBot.value = 1;
      socket.send(JSON.stringify({
        event: "startBot",
        user_id: store.state.user.id
      }))
    }

    const stopBot = () => {
      playByBot.value = 0;
      socket.send(JSON.stringify({
        event: "stopBot",
        user_id: store.state.user.id
      }))
    }

    return {
      role,
      dice_pic,
      playByBot,
      putNum,
      roll,
      startBot,
      stopBot
    }
  }
}
</script>

<style scoped>
div.group_but>button {
  width: 100px;
  height: 100px;
  border-radius: 20%;
  margin: 5px 5px 5px 5px;
  border-style: none;
}

div.group_but {
  width: 330px;
  height: 330px;
  margin: 6vh auto;
}

div.diceGround {
  width: 100px;
  height: 100px;
  margin: 2vh auto;
}

div.role {
  text-align: center;
  color: antiquewhite;
  font-size: 50px;
  font-weight: 600;
}

img {
  width: 100%;
  border-radius: 25%;
}

div.username {
  font-size: 24px;
  font-weight: 600;
  color: black;
  text-align: left;
}

div.rating {
  font-size: 24px;
  font-weight: 600;
  color: antiquewhite;
  font-style: oblique;
  text-align: left;
}

div.Rusername {
  font-size: 24px;
  font-weight: 600;
  color: black;
  text-align: right;
}

div.Rrating {
  font-size: 24px;
  font-weight: 600;
  color: antiquewhite;
  font-style: oblique;
  text-align: right;
}
</style>