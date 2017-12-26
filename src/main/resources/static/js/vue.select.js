// select 插件
Vue.component('vue-select', {
    props: ['options', 'clazz', 'id'],
    template: '<select   v-bind:id="id" v-bind:class="clazz">' +
    '<option value="">全部</option>' +
    '<option v-for="option in options" :value="option.key">{{option.value}}</option> ' +
    '</select>'
});