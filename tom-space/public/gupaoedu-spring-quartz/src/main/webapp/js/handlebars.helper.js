/**
 * 处理索引，给索引加1
 */
Handlebars.registerHelper("seriNum",function(index,options){
    //利用+1的时机，在父级循环对象中添加一个_index属性，用来保存父级每次循环的索引
    this._index = index + 1;
    //返回+1之后的结果
    return this._index;
});

/**
 * 将json对象转换为字符串
 */
Handlebars.registerHelper("tojson",function(o){
    if(null == o || undefined == o){return "";}
    return JSON.stringify(o);
});

/**
 * 计算两个时间差，并格式化
 */
Handlebars.registerHelper("subformattime",function(systime,time){
    return subformattime(systime,time);
});

/**
 * 格式化时间
 */
Handlebars.registerHelper("formattime",function(val,formatter){
    if(val == undefined || val == null){return "";}
     return new Date(val).format(formatter);
});

/**
 * 字符串截取
 */
Handlebars.registerHelper("ellipsis",function(val,len){
    return val.limit(len,"...");
});


/**
 * 比较两个值是否相等
 */
Handlebars.registerHelper("get",function(arr,index,field){
    if(null == arr || undefined == arr || arr.length == 0 || "" == arr || 0 == arr){
        return "";
    }else{
        return arr[index][field];
    }
});




/**
 * 判断数组长度是否为空
 * 判断数字是否为零
 * 判断对象是否为空
 */
Handlebars.registerHelper("isEmpty",function(obj,options){
   if(null == obj || undefined == obj || obj.length == 0 || "" == obj || 0 == obj){
      return options.fn(this);
   }else{
       return options.inverse(this);
   }
});

/**
 * 比较两个值是否不相等
 */
Handlebars.registerHelper("noteq",function(v1,v2,options){
    if(v1 != v2){
        //满足添加继续执行
        return options.fn(this);
    }else{
        //不满足条件执行{{else}}部分
        return options.inverse(this);
    }
});

/**
 * 比较两个值是否相等
 */
Handlebars.registerHelper("eq",function(v1,v2,options){
    if(v1 == v2){
        //满足添加继续执行
        return options.fn(this);
    }else{
        //不满足条件执行{{else}}部分
        return options.inverse(this);
    }
});


/**
 * 比较两个值的大小,判断v1是否大于v2
 */
Handlebars.registerHelper("gt",function(v1,v2,options){
    if(v1 > v2){
        //满足添加继续执行
        return options.fn(this);
    }else{
        //不满足条件执行{{else}}部分
        return options.inverse(this);
    }
});

/**
 * 比较两个值是否相等
 */
Handlebars.registerHelper("toFixed",function(num,len){
    if(null == num || undefined == num || isNaN(num)){
        return num;
    }else{
        with(Math){
            return round(num*pow(10,len))/pow(10,len);
        }//else{
            //return num;
        //}
    }
});



Handlebars.registerHelper("convertSize",function(size){
    if(null == size || undefined == size || isNaN(size)){
        return size;
    }else{
        return convertSize(size);
    }
});