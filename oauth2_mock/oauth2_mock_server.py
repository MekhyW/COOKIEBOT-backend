import uvicorn
from fastapi import FastAPI, HTTPException
from fastapi.responses import JSONResponse
from pydantic import BaseModel
import jwt
import datetime
from jwt import PyJWTError
from cryptography.hazmat.primitives.asymmetric import rsa
import base64


app = FastAPI()

private_key = rsa.generate_private_key(
    public_exponent=65537,
    key_size=2048
)
public_key = private_key.public_key()


def get_jwk():
    public_numbers = public_key.public_numbers()
    e = base64.urlsafe_b64encode(public_numbers.e.to_bytes(3, "big")).decode("utf-8").strip("=")
    n = base64.urlsafe_b64encode(public_numbers.n.to_bytes(256, "big")).decode("utf-8").strip("=")
    key_id = "test-key-id"

    return {
        "kty": "RSA",
        "use": "sig",
        "kid": key_id,
        "n": n,
        "e": e,
        "alg": "RS256",
    }


@app.get("/.well-known/jwks.json")
def get_jwks():
    return JSONResponse(content={"keys": [get_jwk()]})


class TokenRequest(BaseModel):
    username: str
    roles: list[str]


@app.post("/generate-token")
def generate_token(request: TokenRequest):
    try:
        key_id = get_jwk()["kid"]
        payload = {
            "sub": request.username,
            "roles": request.roles,
            "iat": datetime.datetime.utcnow(),
            "exp": datetime.datetime.utcnow() + datetime.timedelta(hours=1),
            "iss": "http://localhost:8000",
            "kid": key_id
        }
        token = jwt.encode(payload, private_key, algorithm="RS256")
        return {"token": token}
    except PyJWTError as e:
        raise HTTPException(status_code=500, detail=f"Token generation failed: {str(e)}")


if __name__ == "__main__":
    uvicorn.run(app, host="127.0.0.1", port=8000)