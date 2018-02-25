<template>
  <div v-if="article.type">
    <div class="form-group" v-for="item in article.type.items" :key="item.id">
      <label>{{item.itemName}}</label>
      <div class="item_description" v-show="item.description">
        {{item.description}}
      </div>
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
          <textarea class="form-control edit-text" v-model="item.itemValue" :name="'item_' + item.itemNo" rows="3"></textarea>
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

      <span v-if="item.itemType === 21">
        <div class="input-group">
          <span class="input-group-addon">
            <span class="fa fa-clock-o"></span>
          </span>
          <clock-picker v-model="item.itemValue" :id="'item_' + item.itemNo">
          </clock-picker>
        </div>
      </span>
      <span v-if="item.itemType === 22">
        <div class="input-group">
          <span class="input-group-addon">
            <span class="fa fa-globe"></span>
          </span>
          <input :id="'item_' + item.itemNo" class="form-control" type="text" :value="item.itemValue" autocomplete="off">
        </div>
        <typeahead v-model="item.itemValue" :data="timezonedata" :target="'#item_' + item.itemNo"/>
      </span>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import logger from 'logger'

import flatPickr from 'vue-flatpickr-component'
import 'flatpickr/dist/flatpickr.css'

import 'flatpickr/dist/themes/material_blue.css'
import {Japanese} from 'flatpickr/dist/l10n/ja'

import ClockPicker from '../Parts/ClockPicker'

import {Typeahead} from 'uiv'
import timezones from 'moment-timezone/data/meta/latest.json'

const LABEL = 'ArticleEditItems.vue'

export default {
  name: 'ArticleEditItems',
  data () {
    var timezonedata = []
    for (var zone in timezones.zones) {
      timezonedata.push(zone)
    }
    logger.trace(LABEL, JSON.stringify(timezonedata))
    return {
      config: {
        wrap: true,
        altFormat: 'Y-m-d',
        altInput: true,
        dateFormat: 'Y-m-d',
        locale: Japanese
      },
      timezonedata: timezonedata
    }
  },
  components: {
    flatPickr, ClockPicker, Typeahead
  },
  computed: {
    ...mapState({
      article: state => state.article.article
    })
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style>
.item_description {
  font-size: 12px;
  position: relative;
  background: #dedede;
  border: 2px solid #55626b;
  margin: 0 5px 5px 30px;
  padding: 3px 10px;
  border-radius: 5px;
  z-index: 5;
}
.item_description:after, .item_description:before {
  top: 100%;
  left: 10%;
  border: solid transparent;
  content: " ";
  height: 0;
  width: 0;
  position: absolute;
  pointer-events: none;
}
.item_description:after {
  border-color: rgba(222, 222, 222, 0);
  border-top-color: #dedede;
  border-width: 11px;
  margin-left: -11px;
}
.item_description:before {
  border-color: rgba(85, 98, 107, 0);
  border-top-color: #55626b;
  border-width: 14px;
  margin-left: -14px;
}
</style>
