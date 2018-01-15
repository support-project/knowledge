<template>
  <div v-if="resources.article.type">
    <div class="form-group" v-for="item in resources.article.type.items" :key="item.id">
      <label>{{item.itemName}} - {{item.itemType}}</label>

      <span v-if="item.itemType === 0">
        <div class="input-group">
          <span class="input-group-addon">
            <span class="fa fa-pencil"></span>
          </span>
          <input type="text" class="form-control" v-model="item.itemValue" :name="'item_' + item.itemNo" />
        </div>
      </span>
      <span v-if="item.itemType === 1">
        <div class="input-group">
          <span class="input-group-addon">
            <span class="fa fa-pencil"></span>
          </span>
          <textarea class="form-control"  v-model="item.itemValue" :name="'item_' + item.itemNo" rows="3"></textarea>
        </div>
      </span>
      <span v-if="item.itemType === 2">
        <div class="input-group">
          <span class="input-group-addon">
            <span class="fa fa-calculator"></span>
          </span>
          <input type="number" class="form-control" v-model="item.itemValue" min="0" step="1" :name="'item_' + item.itemNo" />
        </div>
      </span>
      <span v-if="item.itemType === 10">
        <br/>
        <label class="radio-inline" v-for="choice in item.choices" :key="choice.choiceNo">
          <input type="radio" class="" :value="choice.choiceValue" v-model="item.itemValue" :name="'item_' + item.itemNo" />
          &nbsp;{{choice.choiceLabel}}
        </label>
      </span>
      <span v-if="item.itemType === 11">
        <br/>
        <label class="checkbox-inline" v-for="choice in item.choices" :key="choice.choiceNo">
          <input type="checkbox" class="" :value="choice.choiceValue" v-model="item.itemValue" :name="'item_' + item.itemNo" />
          &nbsp;{{choice.choiceLabel}}
        </label>
      </span>
  
      <span v-if="item.itemType === 20">
        <div class="input-group">
          <span class="input-group-addon">
            <span class="fa fa-calendar"></span>
          </span>
          <flat-pickr
                v-model="item.itemValue"
                :config="config"
                class="form-control"
                placeholder="Select date"
                name="date">
          </flat-pickr>
        </div>
      </span>

    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

import flatPickr from 'vue-flatpickr-component'
import 'flatpickr/dist/flatpickr.css'

import 'flatpickr/dist/themes/material_blue.css'
import {JA} from 'flatpickr/dist/l10n/ja'

export default {
  name: 'ArticleEditItems',
  data () {
    return {
      config: {
        wrap: true,
        altFormat: 'Y-m-d',
        altInput: true,
        dateFormat: 'Y-m-d',
        locale: JA
      }
    }
  },
  components: {
    flatPickr
  },
  computed: {
    ...mapState([
      'resources'
    ])
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>
