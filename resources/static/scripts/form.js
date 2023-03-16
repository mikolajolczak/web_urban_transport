function isNumberKey(evt){
    let charCode = (evt.which) ? evt.which: evt.keyCode
    if(charCode > 31 && (charCode <48 || charCode >57))
        return false
    return true
}
function checkAccount(evt){
    let length = evt.target.value.toString().length
    if(length==26 || length==0){
        evt.target.classList.remove('err')
    }
    else{
        evt.target.classList.add('err')
    }
}
function checkPhone(evt){
    let length = evt.target.value.toString().length
    if(length==9 || length==0){
        evt.target.classList.remove('err')
    }
    else{
        evt.target.classList.add('err')
    }
}
function checkPesel(evt){
    let length = evt.target.value.toString().length
    if(length==11 || length==0){
        evt.target.classList.remove('err')
    }
    else{
        evt.target.classList.add('err')
    }
}
function checkCode(evt){
    let value = evt.target.value.toString()

    if(length==6 || length==0){
        evt.target.classList.remove('err')
    }
    else{
        evt.target.classList.add('err')
    }
}
function isCodeLegit(evt){
    let charCode = (evt.which) ? evt.which: evt.keyCode
    let length = evt.target.value.toString().length
    if(length<2){
    if((charCode > 31 && (charCode <48 || charCode >57))){
        return false
    }

    }
    if(length==2){
    if(!(charCode > 31 && (charCode <48 || charCode >57))){
        evt.target.value = evt.target.value+"-"
        }
        else{
            return false
        }
    }
    if(length>2){
        if((charCode > 31 && (charCode <48 || charCode >57))){
                return false
            }
    }
    if(length>5){
        return false
    }
    return true
}