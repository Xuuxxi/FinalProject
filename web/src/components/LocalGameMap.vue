<template>
  <div class="row">
    <div class="col-5">
      <div class="group_but">
        <div class="role">Rebecca GROUND</div>
        <button v-for="a in aMap" :key="a.id" :value="a.id" @click="putNum(0,a.id)" :style="'background: url(' + dice_pic[a.num] + ') no-repeat;'"></button>
      </div>
    </div>
    <div class="col-2">
      <div class="diceGround">
        <input type="checkbox" id="dice" @click="roll" :checked="dice_num">

        <label for="dice">
          <div
            :class="dice_num === 1 ? 'dice1' : (dice_num === 2 ? 'dice2' : ((dice_num === 3 ? 'dice3' : (dice_num === 4 ? 'dice4' : (dice_num === 5 ? 'dice5' : (dice_num === 6 ? 'dice6' : 'dice'))))))">
          </div>
        </label>
      </div>
      <div class="badge bg-primary text-wrap" style="width: 100%;margin: 0 auto;">
        {{msg}}
      </div>
    </div>
    <div class="col-5">
      <div class="group_but">
        <div class="role">Lucy GROUND</div>
        <button v-for="b in bMap" :key="b.id" :value="b.id" @click="putNum(1,b.id)" :style="'background: url(' + dice_pic[b.num] + ') no-repeat;'"></button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useStore } from 'vuex';
import '@/assets/css/DiceCss.css';

export default {
  setup() {
    let aMap;
    let bMap;
    let dice_num;
    let step;
    let msg;
    const store = useStore();
    let dice_pic = ref([
      'https://pic1.imgdb.cn/item/6336928816f2c2beb15f2b09.png',
      'https://pic1.imgdb.cn/item/63368a3216f2c2beb15612c9.png',
      'https://pic1.imgdb.cn/item/63368a3216f2c2beb15612d0.png',
      'https://pic1.imgdb.cn/item/63368a3216f2c2beb15612c3.png',
      'https://pic1.imgdb.cn/item/63368a3516f2c2beb1561793.png',
      'https://pic1.imgdb.cn/item/63368a3516f2c2beb15617a2.png',
      'https://pic1.imgdb.cn/item/63368a3516f2c2beb15617b1.png'
    ]);

    const init = () => {
      aMap = ref([{ id: 0, num: 0 }, { id: 1, num: 0 }, { id: 2, num: 0 }, { id: 3, num: 0 }, { id: 4, num: 0 }, { id: 5, num: 0 }, { id: 6, num: 0 }, { id: 7, num: 0 }, { id: 8, num: 0 }]);
      bMap = ref([{ id: 0, num: 0 }, { id: 1, num: 0 }, { id: 2, num: 0 }, { id: 3, num: 0 }, { id: 4, num: 0 }, { id: 5, num: 0 }, { id: 6, num: 0 }, { id: 7, num: 0 }, { id: 8, num: 0 }]);
      dice_num = ref(0);
      step = ref(0);
      msg = ref('现在是A回合');
    }

    init();

    const roll = () => {
      dice_num.value = parseInt(Math.random() * (7 - 1) + 1 % 10);
    }

    const set_num = (f, pos, number) => {
      let msg = 'success';
      if (step.value === 0 && f === 0) {
        if (aMap.value[pos].num === 0) {
          aMap.value[pos].num = number;
          judge(f, pos, number);
          step.value = 1;
          return msg;
        } else msg = '只能在数字 0 区域放置';
      } else if (step.value === 0 && f === 1) msg = "请操作左侧九宫格";

      if (step.value === 1 && f === 1) {
        if (bMap.value[pos].num === 0) {
          bMap.value[pos].num = number;
          judge(f, pos, number);
          step.value = 0;
        } else msg = '只能在数字 0 区域放置';
      } else if (step.value === 1 && f === 0) msg = "请操作右侧侧九宫格";

      return msg;
    }

    //算分
    const judge_loser = () => {
      let a_score = 0;
      let b_score = 0;
      let loser = '';

      for (let i = 0; i < 3; i++) {
        let a = aMap.value[i * 3].num;
        let b = aMap.value[i * 3 + 1].num;
        let c = aMap.value[i * 3 + 2].num;

        if (a == b == c) a_score += a * 9;
        else if (a === b) a_score += a * 4 + c;
        else if (a === c) a_score += a * 4 + b;
        else if (b === c) a_score += b * 4 + a;
        else a_score += (a + b + c);
      }

      for (let i = 0; i < 3; i++) {
        let a = bMap.value[i * 3].num;
        let b = bMap.value[i * 3 + 1].num;
        let c = bMap.value[i * 3 + 2].num;

        if (a == b == c) b_score += a * 9;
        else if (a === b) b_score += a * 4 + c;
        else if (a === c) b_score += a * 4 + b;
        else if (b === c) b_score += b * 4 + a;
        else b_score += (a + b + c);
      }

      if (a_score > b_score) loser = 'A';
      else if (a_score < b_score) loser = 'B';
      else loser = 'all';

      store.commit("updateAS", a_score);
      store.commit("updateBS", b_score);
      store.commit("updateLoser", loser);
    }

    //判断棋盘
    const judge_full = () => {
      let arr = [];

      //判断上一回合
      if (step.value === 1) arr = aMap.value;
      else arr = bMap.value;

      let cnt = 0;
      for (let i = 0; i < arr.length; i++) if (arr[i].num != 0) cnt++;
      if (cnt === 9) {
        //判断赢家
        judge_loser();
      }
    }

    //合法操作 消除相同
    const judge = (f, pos, num) => {
      if (f === 0) {
        for (let i = parseInt(pos / 3) * 3; i < parseInt(pos / 3) * 3 + 3; i++) {
          if (bMap.value[i].num === num) bMap.value[i].num = 0;
        }
      } else {
        for (let i = parseInt(pos / 3) * 3; i < parseInt(pos / 3) * 3 + 3; i++) {
          if (aMap.value[i].num === num) aMap.value[i].num = 0;
        }
      }
    }

    const putNum = (f, pos) => {
      if (dice_num.value === 0) {
        alert('请先投掷骰子');
        return;
      }
      let res = set_num(f, pos, dice_num.value)
      if (res != 'success') alert(res);
      else {
        judge_full();
        dice_num.value = 0;
        msg.value = (msg.value === '现在是A回合' ? '现在是B回合' : '现在是A回合');
      }
    }

    return {
      aMap,
      bMap,
      dice_num,
      step,
      msg,
      dice_pic,
      putNum,
      roll
    }
  }
}
</script>

<style scoped>
button {
  width: 100px;
  height: 100px;
  border-radius: 20%;
  margin: 5px 5px 5px 5px;
  border-style: none;
}

div.group_but {
  width: 330px;
  height: 330px;
  margin: 10vh auto;
}

div.diceGround {
  width: 110px;
  height: 110px;
  margin: 2vh auto;
}

div.role {
  text-align: center;
  color: antiquewhite;
  font-size: 50px;
  font-weight: 600;
}
</style>