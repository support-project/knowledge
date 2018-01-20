<template>
  <div>
    <div :class="'alert alert-dismissible alert-' + item.type"
      v-for="item in pagestate.alerts" dismissible :key="item.key"
      v-if="item.display">
      <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
      <h4><i :class="'icon fa ' + item.icon"></i>
      {{ item.title }}
      </h4>
      {{ item.content }}
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'Alerts',
  computed: {
    ...mapState([
      'pagestate'
    ])
  }
}
</script>
