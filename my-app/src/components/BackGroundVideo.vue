<template>
  <div class="homepage-hero-module">
    <div class="video-container">
      <div :style="fixStyle" class="filter"></div>
      <video :style="fixStyle" autoplay loop class="fillWidth" v-on:canplay="canplay" muted>
        <source src="../imgs/Underground-Traffic.webm" type="video/mp4"/>
        浏览器不支持 video 标签，建议升级浏览器。
      </video>
      <div class="poster hidden" v-if="!vedioCanPlay">
        <img :style="fixStyle" src="../imgs/Underground-Traffic.jpg" alt="">
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: 'backgourndvideo',
        data() {
            return {
                vedioCanPlay: false,
                fixStyle: ''
            }
        },
        methods: {
            canplay() {
                this.vedioCanPlay = true
            }
        },
        mounted: function() {
            window.onresize = () => {
                const windowWidth = document.body.clientWidth
                const windowHeight = document.body.clientHeight
                const windowAspectRatio = windowHeight / windowWidth
                let videoWidth
                let videoHeight
                videoWidth = windowWidth
                videoHeight = videoWidth * 0.5625
                this.fixStyle = {
                    height: windowWidth * 0.5625 + 'px',
                    width: windowWidth + 'px',
                }
            }
            window.onresize()
        }
    }
</script>

<style scoped>
  .homepage-hero-module,
  .video-container {
    position: relative;
    height: 100vh;
    width:100vw;
    overflow: hidden;
  }

  .video-container .poster img,
  .video-container video {
    z-index: 0;
    position: absolute;
    width:100%;
    margin-top: -25px;
  }

  .video-container .filter {
    z-index: 1;
    position: absolute;
    background: rgba(256,256,256,0.4);
  }

  .filter,.fillWidth{
    width: 100vw;
    height: 100vh;
  }
</style>
