<template>
  <div> <SearchMenu></SearchMenu></div>

</template>

<script>
  import SearchMenu from '../components/SearchMenu';
  export default {
    name: 'Search',
    components: {
      SearchMenu
    },
    data: () => ({
      }
    ),
    mounted() {
      this.$store.commit('handleSearch')
    }
  }

</script>

<style scoped>

</style>
