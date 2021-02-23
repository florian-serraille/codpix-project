package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description("should registre a valid bank request and return 201 http code")

    request {
        url "/api/v1/bank"
        method "POST"
        headers {
            header "Content-Type": "application/json"
        }
        body(
                institutionCode: "001",
                name: "Banco do Brasil"
        )
    }

    response {
        status CREATED()
        headers {
            contentType applicationJson()
        }
        body(
                code: value(anyNonEmptyString()),
                institutionCode: "001",
                name: "Banco do Brasil"
        )
    }
}