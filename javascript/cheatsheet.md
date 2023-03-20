- IIIF Immediately invoked function expression) tutorial 
- Recursive Functions 
- Nested Functions 
- Anonymous functions
- classes and objects
  - Classes
  - Constructor 
  - Properties 
  - Getters and Setters
  - Inheritance
  - Prototype

## Global methods
- Number.isNaN() or isNaN()
- Decoding and Encoding URI 
  - encodeURI() function
  - decodeURI() function
  - encodeURIComponent()
  - decodeURIComponent()
  - parseInt() and parseFloat() function
  - executing javascript with eval() function
  - creating substring with string slice method
    - ```javascript
            let str = "Create a substring";
            let substr1 = str.slice(5);
            let substr2 = str.slice(0,3);
            console.log("1:", substr1);
                console.log("2:", substr2);
        ```

    - Replacing the parts of substring with replace

        ```javascript
            let hi = "Hi buddy";
            let new_hi = hi.replace("buddy", "Pascal");
            console.log(new_hi);`
        ```
    
    - Check something is not a number
        ```javascript
            let x = 34;
            console.log(isNaN(x));
            console.log(!isNaN(x));
            let str = "hi";
            console.log(isNaN(str));

        ```

    - Check something is finite
        ```javascript
        let x = 3;
        let str = "finite";
        console.log(isFinite(x));
        console.log(isFinite(str));
        console.log(isFinite(Infinity));
        console.log(isFinite(10 / 0));
        ```

    - Check something is Integer
  
        ```javascript
        let x = 3;
        let str = "integer";
        console.log(Number.isInteger(x));
        console.log(Number.isInteger(str));
        console.log(Number.isInteger(Infinity));
        console.log(Number.isInteger(2.4));
        ```     
    - Specifying a number of decimal
        ```javascript
        let x = 1.23456;
        let newX = x.toFixed(2);
        ```
    - Specifying precision
        ```javascript
            let x = 1.23456;
            let newX = x.toPrecision(2);

            let x = 1.23456;
            let newX = x.toPrecision(4);
            console.log(newX)
        ```

    - Turning decimals into integers
        ```javascript
            let x = 6.78;
            let y = 5.34;
            console.log("X:", x, "becomes", Math.round(x));
            console.log("Y:", y, "becomes", Math.round(y));
        ```

## Javascript Date
 ```javascript
    let d = new Date();
    console.log("Day of week:", d.getDay());
    console.log("Day of month:", d.getDate());
    console.log("Month:", d.getMonth());
    console.log("Year:", d.getFullYear());
    console.log("Seconds:", d.getSeconds());
    console.log("Milliseconds:", d.getMilliseconds());
    console.log("Time:", d.getTime());

    d.setFullYear(2010);
    console.log(d);

    d.setMonth(9);
    console.log(d);

    d.setDate(10);
    console.log(d);

```

- Parsing Dates

```javascript
let d1 = Date.parse("June 5, 2021");
console.log(d1);

// This will log:
// 1622851200000

let d2 = Date.parse("6/5/2021");
console.log(d2);
// This will also log:
// 1622851200000
console.log(d.toDateString());

// This will log the day in written format:
// Sat Jun 05 2021

console.log(d.toLocaleDateString());
// It will log: 6/5/2021


```
  

## DOM 
```javascript
console.dir(window);
window.history.length;
window.history.go(-1);
console.dir(window.navigator);
console.dir(document);
const ele1 = document.querySelector("h1");
console.dir(ele1);

document.querySelector("div");
document.querySelector(".something");
document.querySelectorAll("div");

```

- manipulating element style
```javascript
<script>
 function toggleDisplay(){
 let p = document.getElementById("magic");
 if(p.style.display === "none") {
 p.style.display = "block";
 } else {
 p.style.display = "none";
 }
 }
 </script>
 ```

 - Adding classes to elements
```javascript 
function disappear(){
 document.getElementById("shape").classList.add("hide");
 }

 ```

 - Event listeners on elements
```javascript
<button onclick="addRandomNumber()">Add a number</button>
```

- Even though it is two steps, it can be done in one line of code:
  
```javascript
document.getElementById("square").addEventListener("click",
changeColor);
```

- Creating new elements

```javascript
let el = document.createElement("p");
el.innerText = Math.floor(Math.random() * 100);
document.body.appendChild(el);
```

## Mouse event handlers

- ondblclick: when the mouse is double-clicked
- onmousedown: when the mouse clicks on top of an element without the click being released
- onmouseup: when the mouse click on top of an element is released
- onmouseenter: when the mouse moves onto an element
- onmouseleave: when the mouse leaves an element and all of its children
- onmousemove: when the mouse moves over an element
- onmouseout: when the mouse leaves an individual element
- onmouseover: when the mouse hovers over an element


## Regular expressions
- `/([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z0-9._-]+)/g` this regex check the valid email address.
  
- Matching
```javascript 
// There is no match here
let text = "I love JavaScript!";
console.log(text.match(/javascript/));
```

- Case sensitive matching
```javascript
console.log(text.match(/javascript/i));
```

- Specifying multiple options for words
```javascript
// Here, the expression matches either javascript, nodejs, or react. At this point, we are only matching for the first encounter and then we quit
let text = "I love JavaScript!";
console.log(text.match(/javascript|nodejs|react/i));

```

- If you want to match all the thing use the global identifier. 
```javascript
let text = "I love React and JavaScript!";
console.log(text.match(/javascript|nodejs|react/gi));
```

##  Functions and the arguments object
##  JavaScript hoisting
##  Strict mode
##  Debugging
##  Using cookies
##  Local storage
##  JSON Javascript 
