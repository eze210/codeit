class WordNumberHandlingTagLib {

    def wordFromNumber = { attrs ->
        String plural = attrs.containsKey("plural") ? attrs.plural : (attrs.word + "s")
        out << wordAccordingToNumber(attrs.number.toInteger(), attrs.word, plural)
    }

    def quantityOf = {attrs ->
        String plural = attrs.containsKey("plural") ? attrs.plural : (attrs.word + "s")
        out << attrs.number + " " + wordAccordingToNumber(attrs.number.toInteger(), attrs.word, plural)
    }

    String wordAccordingToNumber(Integer number, String word, String plural) {
        return number == 1 ? word : plural
    }

}