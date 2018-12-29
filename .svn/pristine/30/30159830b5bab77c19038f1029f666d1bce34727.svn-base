/**
 * Console output
 * 
 * @param {Object}
 *            obj
 */
function log(obj) {
	obj = Validate.isNullOrEmpty(obj) ? obj : Validate.isString(obj) ? message : JSON.stringify(obj);
	obj = '[' + DateUtility.getNow('hh:mm') + ']	' + obj;

	if(typeof console == 'object')
		console.log(obj);
	else if(typeof opera == 'object')
		opera.postError(obj);
	else if(typeof java == 'object' && typeof java.lang == 'object')
		java.lang.System.out.println(obj);
}

/**
 * Get object type
 * 
 * @param obj
 * @returns
 */
function typeOf(obj) {
	var _toString = Object.prototype.toString;

	var _types = {
		"undefined": "undefined",
		"number": "number",
		"string": "string",
		"[object Function]": "function",
		"[object RegExp]": "regexp",
		"[object Array]": "array",
		"[object Date]": "date",
		"[object Error]": "error"
	}

	return _types[typeof obj] || _types[_toString.call(obj)] || (obj ? "object" : "null");
}

/**
 * The bind function provides an optional way to perform context passing to a
 * function. <br>
 * and returns a function inside the bind function to correct changes
 * in the execution context that occur on the function call
 */
function bind(fn, context) {
	return function() {
		return fn.apply(context, arguments);
	}
}

/**
 * Assertions are used primarily for parameter validation
 */
var Assert = {

	/**
	 * Validation is not empty
	 * 
	 * @param {Object}
	 *            obj Verify the object
	 * @param {Object}
	 *            message Prompt information
	 */
	notNull: function(obj, message) {
		if(Validate.isNull(obj)) {
			throw new IllegalArgumentException(arguments.callee + " " + message);
		}

	},
	/**
	 * Validation expression
	 * 
	 * @param {Object}
	 *            expression
	 * @param {Object}
	 *            message
	 */
	state: function(expression, message) {
		if(!expression)
			throw new IllegalArgumentException(message);
	}

}

/**
 * validate Check class
 */
var Validate = {
	/**
	 * Verify that it is null （null/undefined/NaN）
	 * 
	 * @param {Object}
	 *            obj
	 */
	isNull: function(obj) {
		if(obj == null || obj == undefined || obj == NaN)
			return true;

		return false;
	},

	/**
	 * Verifies that it is null or empty
	 * 
	 * @param {Object}
	 *            obj
	 */
	isNullOrEmpty: function(obj) {
		if(obj != 0 && !obj) return true;

		if(typeof(obj) == "string" && !obj.toString().replace(/(^\s*)|(\s*$)/g, ''))
			return true;

		if(Validate.isArray(obj) && obj.length < 1)
			return true;

		return false;
	},
	isString: function(obj) {
		return typeof(obj) == "string"
	},
	isNumber: function(obj) {
		return typeof(obj) == "number" && isFinite(obj);
	},
	isArray: function(obj) {
		if(typeof Array.isArray === "function")
			return Array.isArray(obj);
		else
			return Object.prototype.toString.call(value) === "[object Array]";
	},
	isObject: function(obj) {
		return obj != null && typeof(obj) == "object";
	},
	isEmail: function(email) {
		var regexp = '^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$';
		return email.search(regexp) != -1;

	},
	isPhone: function(email) {
		var regexp = '/^[1][3,4,5,7,8][0-9]{9}$/';
		return email.search(regexp) != -1;

	},

};

/**
 * Date tool class
 */
var DateUtility = {
	/**
	 * Get the current time
	 * 
	 * @param {Object}
	 *            format
	 */
	getNow: function(format) {
		return this._format(format ? format : 'yyyy-MM-dd hh:mm:ss', new Date);
	},
	getNowForInt: function() {
		return Number.parseInt((new Date().getTime()) / 1000);
	},
	intToString: function(timeInt, format) {
		return this._format(format ? format : 'yyyy-MM-dd hh:mm:ss', new Date(timeInt * 1000));
	},
	/**
	 * Format time
	 * 
	 * @param {Object}
	 *            fmt
	 * @param {Object}
	 *            date
	 */
	_format: function(fmt, date) {
		var o = {
			"M+": date.getMonth() + 1, // 月份
			"d+": date.getDate(), // 日
			"h+": date.getHours(), // 小时
			"m+": date.getMinutes(), // 分
			"s+": date.getSeconds(), // 秒
			"q+": Math.floor((date.getMonth() + 3) / 3), // 季度
			"S": date.getMilliseconds() // 毫秒
		};
		if(/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
		for(var k in o)
			if(new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
}

/**
 * reflection
 */
var Reflect = {

	/**
	 * Call the specified function
	 * 
	 * @param {Object}
	 *            methodName
	 */
	invokeMethod: function(methodName) {
		try {
			var args = "";
			for(var i = 1; i < arguments.length; i++) {
				args += Validate.isString(arguments[i]) ? '\'' + arguments[i] + '\'' : arguments[i];
				if(i < arguments.length - 1)
					args += ',';
			}
			eval(methodName + '(' + args + ')');
		} catch(err) {
			throw new InvokeException('Unknown function  : ' + methodName + '(' + args + ')')
		}
	},
	/**
	 * Dynamic creation function<br>
	 * Reflect.newMethod('a','b','return a+b')(1,1) -> Output: 2
	 */
	newMethod: function() {
		var args = new Array;
		for(var i = 0; i < arguments.length; i++)
			args.add(arguments[i]);
		return Function.apply(Object, args);
	}
}

/**
 * Calculation tool class
 */
var MathUtility = {
	/**
	 * Retention decimal point
	 * 
	 * @param {Object}
	 *            number
	 * @param {Object}
	 *            digit
	 */
	keepDecimalPoint: function(number, digit) {
		Assert.state(Validate.isNumber(number, digit), "Parameter should be of numeric type by number：  " + number);
		return number.toFixed(digit);
	}

}

/**
 * Insert elements
 */
Array.prototype.insert = function(index) {
	Assert.notNull(this, 'array is null');
	Assert.notNull(index, 'index is ' + index);
	Assert.state(Validate.isNumber(index), 'Illegal index : ' + index);
	for(var i = 2; i < arguments.length; i++)
		this.splice(index++, 0, arguments[i]);
}
/**
 * Add elements
 * 
 * @param {Object}
 *            item
 */
Array.prototype.add = function(item) {
	Assert.notNull(this, 'Array is null');
	this.push(item);
}
/**
 * Remove elements
 * 
 * @param {Object}
 *            obj
 */
Array.prototype.remove = function(obj) {
	Assert.notNull(this, 'array is null');
	for(var i = 0, len = this.length; i < len; i++)
		if(this[i] === obj)
			this.splice(i, 1)
}

Array.prototype.clear = function() {
	Assert.notNull(this, 'array is null');
	this.splice(0);
}

Array.prototype.sub = function(start, end) {
	return this.slice(start, end);
}

Array.prototype.sort = function(comparator) {
	Assert.notNull(this, 'array is null');
	this.sort(comparator);
}

// Abnormal area

function IllegalArgumentException(message) {
	var e = new Error;
	e.name = 'IllegalArgumentException';
	e.message = message;

	return e;
}

function InvokeException(message) {
	var e = new Error;
	e.name = 'InvokeException';
	e.message = message;

	return e;
}