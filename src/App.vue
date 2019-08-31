<template>
  <v-app>
    <NavBar/>
    <transition>
    <router-view/>
    </transition>
  </v-app>
</template>

<script>
import NavBar from "./components/NavBar";

export default {
  name: 'App',
  components: {
    NavBar,
  },
};
</script>
