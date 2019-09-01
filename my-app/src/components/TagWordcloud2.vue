<template>
  <div class="content-box">
    <!-- vue的ref可以通过this.$refs.获取到DOM节点 -->
    <!-- echart容器div必须设置高度，否则不显示 -->
    <div ref="keyWords" style="height:200px; width: 300px"></div>
  </div>
</template>
<script>
  export default {
    data: function() {
      return {
        words: [
          {
            name: "科幻",
            value: 10000
          },
          {
            name: "爱情",
            value: 6181
          },
          {
            name: "动作",
            value: 4386
          },
          {
            name: "古装",
            value: 4055
          },
          {
            name: "都市",
            value: 2467
          },
          {
            name: "励志",
            value: 2244
          },
          {
            name: "复古",
            value: 1898
          },
          {
            name: "历史",
            value: 1484
          },
          {
            name: "记录",
            value: 1112
          },
          {
            name: "正能量",
            value: 965
          },
          {
            name: "鸡汤",
            value: 847
          },
          {
            name: "二次元",
            value: 582
          }
        ]
      };
    },
    props: [

    ],
    mounted() {
      this.$nextTick(() => {
        this.initEchart();
      });
    },
    methods: {
      initEchart() {
        //获取DOM节点并初始化
        let myChart = this.$echarts.init(this.$refs.keyWords);
        let option = {
          tooltip: {
            show: true
          },
          series: [
            {
              name: "",
              type: "wordCloud",
              size: ["95%", "95%"],
              textRotation: [0, 0, 90, 0],
              textPadding: 0,
              autoSize: {
                enable: true,
                minSize: 24
              },
              textStyle: {
                normal: {
                  color: function() {
                    return (
                      "rgb(100,100,100)"
                    );
                  }
                }
              },
              data: this.words
            }
          ]
        };
        //设置图表的参数
        myChart.setOption(option);
      }
    }
  };
</script>
