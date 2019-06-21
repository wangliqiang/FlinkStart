<template>
  <div id="echarts" class="charts"></div>
</template>

<script>
export default {
  name: "topn",
  data() {
    return {
      xData: [],
      yData: [],
      myChart:{}
    };
  },
  sockets: {
    connect() {
      console.log("socket connected");
    },
    message(value) {
      var _this = this;
      _this.xData = [];
      _this.yData = [];
      JSON.parse(value[0].info).forEach(val => {
        _this.xData.push(val.f0);
        _this.yData.push(val.f1);
      });
      this.onDraw();
    }
  },
  methods: {
    initSocket() {
      this.$socket.emit("message", {});
    },
    onDraw() {
      // 绘制图表
      this.myChart.setOption({
        title: {
          text: "图书销量排行"
        },
        tooltip: {},
        legend: {
          data: ["销量"]
        },
        xAxis: {
          data: this.xData,
          axisLabel: {
            interval: 0,
            rotate: 20
          }
        },
        yAxis: {},
        series: [
          {
            name: "销量",
            type: "bar",
            data: this.yData,
            itemStyle: {
              normal: {
                label: {
                  show: true, //开启显示
                  position: "top", //在上方显示
                  textStyle: {
                    //数值样式
                    color: "black",
                    fontSize: 16
                  }
                }
              }
            }
          }
        ]
      });
    }
  },
  created() {
    this.initSocket();
  },
  mounted() {
    var dom = document.getElementById("echarts");
    this.myChart = this.echarts.init(dom);
  }
};
</script>
<style>
.charts {
  width: 600px;
  height: 400px;
  margin: 0 auto;
}
</style>
