function test1(value) {
    for (const val in value) {  //idk co ja tu chcialam, ale tez nie zadzialalo bo uwaza ze funkcja niezdefiniowana
        const s = value[val];
        const url = "http://localhost:8080/solve/" + s;
        Http.open("POST", url);
        Http.send();
        Http.onreadystatechange = (e) => {
            console.log(Http.responseText)
        }
    }
}
