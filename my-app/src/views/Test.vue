<template>
  <div>
    {{info}}
  </div>
</template>

<script>

export default {
  name: 'Test',
  data () {
    return {
      info: null
    }
  },
  mounted () {
    this.axios({
      method: 'post',
      url: 'http://114.115.151.96:8666/reply/list',
      data: {
        id: this.postId,
        type: 4
      },
      crossDomain: true
    }).then(body => {
      this.info = body.data
    })
  }

}
</script>

<style scoped>

</style>
